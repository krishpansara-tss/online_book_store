package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.orderitems.OrderItemRequestDTO;
import com.online_bookstore.app.dtos.orderitems.OrderItemResponseDTO;
import com.online_bookstore.app.models.OrderItem;

import java.util.List;

public interface IOrderItemService {
    List<OrderItem> getListOfOrderItems(List<OrderItemRequestDTO> dtoList);
    OrderItem getOrderItemEntity(OrderItemRequestDTO dto);
    PageResponse<OrderItemResponseDTO> getOrderItemByOrderId(Long orderId, Integer page, Integer size);
    boolean hasUserPurchasedBook(Long userId, Long bookId);
}
