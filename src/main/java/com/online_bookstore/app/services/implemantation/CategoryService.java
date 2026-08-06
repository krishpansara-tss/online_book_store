package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.categories.CategoryRequestDTO;
import com.online_bookstore.app.dtos.categories.CategoryResponseDTO;
import com.online_bookstore.app.exceptions.CategoryNotFoundException;
import com.online_bookstore.app.exceptions.DuplicateResourceException;
import com.online_bookstore.app.mappers.CategoryMapper;
import com.online_bookstore.app.models.Category;
import com.online_bookstore.app.repositories.CategoryRepository;
import com.online_bookstore.app.services.interfaces.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Override
    public CategoryResponseDTO addNewCategory(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        if(categoryRepository.existsByNameIgnoreCase(category.getName())){
            logger.error("Category Having name: {} already exists.", category.getName());
            throw new DuplicateResourceException("Category already exists.");

        }
        Category added_category = categoryRepository.save(category);
        logger.info("Category Having Id: {} Added successfully.", added_category.getCategoryId());
        return categoryMapper.toResponse(added_category);
    }

    @Override
    public PageResponse<CategoryResponseDTO> getAllCategories(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<CategoryResponseDTO> content = categoryPage
                .getContent()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();

        return PageResponse.<CategoryResponseDTO>builder().
                content(content).
                page(categoryPage.getNumber()).
                size(categoryPage.getSize()).
                totalElements(categoryPage.getNumberOfElements()).
                totalPages(categoryPage.getTotalPages()).
                last(categoryPage.isLast()).
                build();
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> {
                    logger.error("Category Having Id: {} doesn't exists.", id);
                    return new CategoryNotFoundException(id);
                }
        );

        return categoryMapper.toResponse(category);
    }

    @Override
    public Category getCategoryEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Category Having Id: {} doesn't exists.", id);
                    return new CategoryNotFoundException(id);
                });
    }

    @Override
    public PageResponse<CategoryResponseDTO> searchCategory(String name, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<Category> categoryPage = categoryRepository.findByNameContainingIgnoreCase(name, pageable);

        List<CategoryResponseDTO> content = categoryPage
                .getContent()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();

        return PageResponse.<CategoryResponseDTO>builder().
                content(content).
                page(categoryPage.getNumber()).
                size(categoryPage.getSize()).
                totalElements(categoryPage.getNumberOfElements()).
                totalPages(categoryPage.getTotalPages()).
                last(categoryPage.isLast()).
                build();
    }

    @Override
    public CategoryResponseDTO updateCategoryById(Long categoryId, CategoryRequestDTO dto) {
        Category category = getCategoryEntityById(categoryId);

        category.setName(dto.getName());
        Category updated_category = categoryRepository.save(category);
        logger.info("Category Having Id: {} Updated successfully.", categoryId);
        return categoryMapper.toResponse(category);
    }
}
