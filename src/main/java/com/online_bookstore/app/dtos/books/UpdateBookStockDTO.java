package com.online_bookstore.app.dtos.books;

import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UpdateBookStockDTO {
    @Min(value = 1, message = "Stock quantity to add must be at least 1.")
    private Long stockToAdd;
}
