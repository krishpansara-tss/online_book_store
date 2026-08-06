package com.online_bookstore.app.mappers;

import com.online_bookstore.app.dtos.orders.OrderRequestDTO;
import com.online_bookstore.app.dtos.orders.OrderResponseDTO;
import com.online_bookstore.app.models.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(OrderRequestDTO dto);
    OrderResponseDTO toResponse(Order Order);
}
