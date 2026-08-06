package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.categories.CategoryResponseDTO;
import com.online_bookstore.app.dtos.orderitems.OrderItemRequestDTO;
import com.online_bookstore.app.dtos.orderitems.OrderItemResponseDTO;
import com.online_bookstore.app.mappers.OrderItemMapper;
import com.online_bookstore.app.models.Book;
import com.online_bookstore.app.models.OrderItem;
import com.online_bookstore.app.repositories.OrderItemRepository;
import com.online_bookstore.app.services.interfaces.IOrderItemService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService implements IOrderItemService {

    private final OrderItemRepository orderItemRepository;

    private final BookService bookService;

    private final OrderItemMapper orderItemMapper;

    @Override
    public boolean hasUserPurchasedBook(Long userId, Long bookId) {
        return orderItemRepository.hasUserPurchasedBook(userId, bookId);
    }

    public List<OrderItem> getListOfOrderItems(List<OrderItemRequestDTO> dtoList){
        return dtoList.stream()
                .map(this::getOrderItemEntity)
                .toList();
    }

    public OrderItem getOrderItemEntity(OrderItemRequestDTO dto){
        Book book = bookService.getBookEntityById(dto.getBookId());
        OrderItem orderItem = orderItemMapper.toEntity(dto);
        orderItem.setBook(book);
        orderItem.setPriceAtPurchase(book.getPrice());

        return orderItem;
    }

    @Override
    public PageResponse<OrderItemResponseDTO> getOrderItemByOrderId(Long orderId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Object[]> orderItemsPage = orderItemRepository.findAllItemsByOrderId(orderId, pageable);

        List<OrderItemResponseDTO> content = orderItemsPage
                .map(row -> {
                    OrderItemResponseDTO dto = new OrderItemResponseDTO();

                    dto.setOrderItemId((Long)row[0]);
                    dto.setTitle((String)row[1]);
                    dto.setQuantity((Long)row[2]);
                    dto.setPriceAtPurchase((Double) row[3]);

                    return dto;
                })
                .toList();


        return PageResponse.<OrderItemResponseDTO>builder().
                content(content).
                page(orderItemsPage.getNumber()).
                size(orderItemsPage.getSize()).
                totalElements(orderItemsPage.getNumberOfElements()).
                totalPages(orderItemsPage.getTotalPages()).
                last(orderItemsPage.isLast()).
                build();
    }
}
