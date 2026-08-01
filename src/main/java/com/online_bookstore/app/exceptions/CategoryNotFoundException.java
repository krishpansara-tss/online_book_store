package com.online_bookstore.app.exceptions;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(Long id) {
        super("Category not found with id: " + id);
    }
}
