package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.orderitems.OrderItemRequestDTO;
import com.online_bookstore.app.dtos.orders.OrderBasicInformationDTO;
import com.online_bookstore.app.dtos.orders.OrderRequestDTO;
import com.online_bookstore.app.dtos.orders.OrderResponseDTO;
import com.online_bookstore.app.exceptions.InsufficientStockException;
import com.online_bookstore.app.exceptions.InvalidOperationException;
import com.online_bookstore.app.exceptions.OrderNotFoundException;
import com.online_bookstore.app.mappers.OrderMapper;
import com.online_bookstore.app.models.Book;
import com.online_bookstore.app.models.Order;
import com.online_bookstore.app.models.OrderItem;
import com.online_bookstore.app.models.User;
import com.online_bookstore.app.repositories.OrderRepository;
import com.online_bookstore.app.services.interfaces.IOrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private final OrderRepository orderRepository;

    private final UserService userService;
    private final BookService bookService;
    private final OrderItemService orderItemService;

    private final OrderMapper orderMapper;

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Transactional
    @Override
    public OrderResponseDTO addNewOrder(Long userId, OrderRequestDTO dto) {
        Order order = orderMapper.toEntity(dto);

        User user = userService.getUserEntityById(userId);
        if(!user.isActive()){
            logger.error("Error to place Order because User having ID: {} is Inactive.", userId);
            throw new InvalidOperationException("Error to place Order because User is Inactive.");
        }

        List<OrderItemRequestDTO> mergedOrderItems = mergeDuplicateBooks(dto.getOrderItemsToAdd());

        List<OrderItem> orderItems = orderItemService.getListOfOrderItems(mergedOrderItems);

        for(OrderItem orderItem : orderItems){
            Book book = orderItem.getBook();
            if(orderItem.getQuantity() > book.getStock()){
                logger.error("Error to place Order because Insufficient Stock of Book having ID: {}", book.getBookId());
                throw new InsufficientStockException(
                        "Insufficient stock for " + book.getTitle()
                );
            }
            if(!orderItem.getBook().isActive()){
                logger.error("Error to place Order because Book having ID: {} is Not Available", book.getBookId());
                throw new InvalidOperationException("Error to place Order because Book is Not Available.");
            }
        }

        for (OrderItem orderItem : orderItems) {
            Book book = orderItem.getBook();
            book.setStock(book.getStock() - orderItem.getQuantity());
            orderItem.setOrder(order);
        }

        order.setOrderAddress(user.getProfile().getAddress());
        order.setUser(user);
        order.setOrderItems(orderItems);
        order.setTotalAmount(calculateOrderTotal(orderItems));

        Order added_order = orderRepository.save(order);

        logger.info("The Order is placed successfully having ID: {}", added_order.getOrderId());

        return orderMapper.toResponse(added_order);
    }

    private List<OrderItemRequestDTO> mergeDuplicateBooks(List<OrderItemRequestDTO> orderItemsToAdd) {
        Map<Long, Long> quantities = new LinkedHashMap<>();

        for(OrderItemRequestDTO orderItem : orderItemsToAdd){
            quantities.merge(
                    orderItem.getBookId(),
                    orderItem.getQuantity(),
                    Long::sum
            );
        }

        return quantities.entrySet()
                .stream()
                .map(entry -> {
                    OrderItemRequestDTO dto = new OrderItemRequestDTO();
                    dto.setBookId(entry.getKey());
                    dto.setQuantity(entry.getValue());
                    return dto;
                })
                .toList();
    }

    @Override
    public PageResponse<OrderBasicInformationDTO> getAllOrders(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<OrderBasicInformationDTO> orderPage = orderRepository.getBasicInformationOfOrders(pageable);

        List<OrderBasicInformationDTO> content = orderPage
                .toList();

        return PageResponse.<OrderBasicInformationDTO>builder()
                .content(content)
                .page(orderPage.getNumber()).
                size(orderPage.getSize())
                .totalElements(orderPage.getTotalElements())
                .totalPages(orderPage.getTotalPages())
                .last(orderPage.isLast())
                .build();
    }


    @Override
    public PageResponse<OrderBasicInformationDTO> getAllActiveOrders(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<OrderBasicInformationDTO> orderPage = orderRepository.getBasicInformationOfActiveOrders(pageable);

        List<OrderBasicInformationDTO> content = orderPage
                .toList();

        return PageResponse.<OrderBasicInformationDTO>builder()
                .content(content)
                .page(orderPage.getNumber()).
                size(orderPage.getSize())
                .totalElements(orderPage.getTotalElements())
                .totalPages(orderPage.getTotalPages())
                .last(orderPage.isLast())
                .build();
    }

    @Override
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> {
                    logger.error("The Order having ID: {} doesn't exists.", orderId);
                    return new OrderNotFoundException(orderId);
                }
        );

        return orderMapper.toResponse(order);
    }

    @Override
    public Order getOrderEntityById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> {
                    logger.error("The Order having ID: {} doesn't exists.", orderId);
                    return new OrderNotFoundException(orderId);
                }
        );
    }

    @Override
    public List<OrderResponseDTO> getOrdersByUserId(Long userId) {
        User user = userService.getUserEntityById(userId);

        List<OrderResponseDTO> response = user.getOrders().stream()
                .map(orderMapper::toResponse)
                .toList();

        return response;
    }

    @Override
    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = getOrderEntityById(orderId);

        if(!order.isActive()){
            logger.error("The Order having ID: {} is already canceled.", orderId);
            throw new InvalidOperationException("The Order is already canceled.");
        }

        List<OrderItem> orderItems = order.getOrderItems();
        for(OrderItem orderItem : orderItems){
            Long orderedQuantity = orderItem.getQuantity();

            bookService.addStockByBookObj(orderItem.getBook(), orderedQuantity);
        }
        logger.info("The Order is canceled successfully having ID: {}", orderId);
        order.setActive(false);
    }

    private Double calculateOrderTotal(List<OrderItem> orderItems){

        return orderItems.stream()
                .mapToDouble(item -> item.getPriceAtPurchase() * item.getQuantity())
                .sum();
    }
}
