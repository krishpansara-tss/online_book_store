package com.online_bookstore.app.dtos.orders;

import com.online_bookstore.app.dtos.orderitems.OrderItemResponseDTO;
import com.online_bookstore.app.dtos.users.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class OrderBasicInformationDTO {
    private Long orderId;
    private Long userId;
    private String name;
    private Double totalAmount;
    private String orderAddress;
}
