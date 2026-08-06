package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.orders.OrderBasicInformationDTO;
import com.online_bookstore.app.dtos.orders.OrderRequestDTO;
import com.online_bookstore.app.dtos.orders.OrderResponseDTO;
import com.online_bookstore.app.models.Order;


import java.util.List;

public interface IOrderService {
    OrderResponseDTO addNewOrder(Long userId, OrderRequestDTO dto);
    PageResponse<OrderBasicInformationDTO> getAllOrders(Integer page, Integer size);
    PageResponse<OrderBasicInformationDTO> getAllActiveOrders(Integer page, Integer size);
    OrderResponseDTO getOrderById(Long OrderId);
    List<OrderResponseDTO> getOrdersByUserId(Long userId);
    void cancelOrder(Long orderId);
    Order getOrderEntityById(Long orderId);
}
