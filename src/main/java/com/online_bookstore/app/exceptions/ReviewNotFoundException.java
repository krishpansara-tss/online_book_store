package com.online_bookstore.app.exceptions;

public class ReviewNotFoundException extends ResourceNotFoundException {
    public ReviewNotFoundException(Long id) {
        super("Review not found with id: " + id);
    }
}
