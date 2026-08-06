package com.online_bookstore.app.dtos.categories;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class CategoryRequestDTO {
    @NotBlank(message = "Category name is required.")
    private String name;
}
