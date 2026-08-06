package com.online_bookstore.app.dtos.orderitems;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class OrderItemRequestDTO {
    @NotNull(message = "Book ID is required.")
    private Long bookId;

    @NotNull(message = "Quantity is required.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    private Long quantity;
}
