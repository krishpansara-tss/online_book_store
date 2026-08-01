package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.categories.CategoryRequestDTO;
import com.online_bookstore.app.dtos.categories.CategoryResponseDTO;
import com.online_bookstore.app.models.Category;

public interface ICategoryService {

    CategoryResponseDTO addNewCategory(CategoryRequestDTO dto);
    PageResponse<CategoryResponseDTO> getAllCategories(Integer page, Integer size);
    CategoryResponseDTO getCategoryById(Long id);
    Category getCategoryEntityById(Long id);

}
