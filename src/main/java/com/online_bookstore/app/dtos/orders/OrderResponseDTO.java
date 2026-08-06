package com.online_bookstore.app.dtos.orders;

import com.online_bookstore.app.dtos.orderitems.OrderItemResponseDTO;
import com.online_bookstore.app.dtos.users.UserResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class OrderResponseDTO {
    private Long orderId;
    private Double totalAmount;
    private UserResponseDTO user;
    private String orderAddress;
    private List<OrderItemResponseDTO> orderItems;
}
