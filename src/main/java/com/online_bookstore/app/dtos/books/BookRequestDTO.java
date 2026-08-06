package com.online_bookstore.app.dtos.books;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class BookRequestDTO {
    @NotBlank(message = "Book title name is required.")
    private String title;

    @NotBlank(message = "Book ISBN is required.")
    private String ISBN;

    @Min(value = 1, message = "Price must be at least 1.")
    private Double price;

    @Min(value = 1, message = "Stock must be at least 1.")
    private Long stock;

    @NotNull(message = "Category id is required.")
    private Long categoryId;

    @NotNull(message = "Publisher id is required.")
    private Long publisherId;

    @NotEmpty(message = "Author ID must contain at least one item.")
    private List<Long> authorIds;
}
