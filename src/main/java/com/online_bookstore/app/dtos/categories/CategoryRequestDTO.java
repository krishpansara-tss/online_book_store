package com.online_bookstore.app.dtos.categories;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class CategoryRequestDTO {
    private String name;
}
