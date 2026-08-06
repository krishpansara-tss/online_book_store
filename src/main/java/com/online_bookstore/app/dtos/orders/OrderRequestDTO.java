package com.online_bookstore.app.dtos.orders;

import com.online_bookstore.app.dtos.orderitems.OrderItemRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class OrderRequestDTO {
    @NotEmpty(message = "Order must contain at least one item.")
    @Valid
    private List<OrderItemRequestDTO> orderItemsToAdd;
}
