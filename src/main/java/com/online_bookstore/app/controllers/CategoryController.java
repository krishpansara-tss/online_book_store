package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.categories.CategoryRequestDTO;
import com.online_bookstore.app.dtos.categories.CategoryResponseDTO;
import com.online_bookstore.app.services.implemantation.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> addNewCategory(@RequestBody CategoryRequestDTO dto){
        CategoryResponseDTO response = categoryService.addNewCategory(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<CategoryResponseDTO>> getAllCategory(@RequestParam(defaultValue = "0") Integer page,
                                                                            @RequestParam(defaultValue = "5") Integer size){
        PageResponse<CategoryResponseDTO> response = categoryService.getAllCategories(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{category_id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Long category_id){
        CategoryResponseDTO response = categoryService.getCategoryById(category_id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
