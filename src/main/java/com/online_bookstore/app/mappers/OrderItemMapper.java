package com.online_bookstore.app.mappers;


import com.online_bookstore.app.dtos.orderitems.OrderItemRequestDTO;
import com.online_bookstore.app.dtos.orderitems.OrderItemResponseDTO;
import com.online_bookstore.app.models.OrderItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderItemMapper {
    OrderItem toEntity(OrderItemRequestDTO dto);
    OrderItemResponseDTO toResponse(OrderItem OrderItem);
}
