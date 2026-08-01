package com.online_bookstore.app.dtos.categories;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class CategoryResponseDTO {
    private Long categoryId;
    private String name;
}
