package com.online_bookstore.app.mappers;

import com.online_bookstore.app.dtos.categories.CategoryRequestDTO;
import com.online_bookstore.app.dtos.categories.CategoryResponseDTO;
import com.online_bookstore.app.models.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category toEntity(CategoryRequestDTO dto);
    CategoryResponseDTO toResponse(Category category);
}
