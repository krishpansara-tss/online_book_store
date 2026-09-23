package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.categories.CategoryRequestDTO;
import com.online_bookstore.app.dtos.categories.CategoryResponseDTO;
import com.online_bookstore.app.models.Category;

import java.util.List;

public interface ICategoryService {

    CategoryResponseDTO addNewCategory(CategoryRequestDTO dto);
    PageResponse<CategoryResponseDTO> getAllCategories(Integer page, Integer size);
    CategoryResponseDTO getCategoryById(Long id);
    Category getCategoryEntityById(Long id);
    CategoryResponseDTO updateCategoryById(Long categoryId, CategoryRequestDTO dto);
    PageResponse<CategoryResponseDTO> searchCategory(String name, Integer page, Integer size);

    List<CategoryResponseDTO> getAllCategoriesList();
}
