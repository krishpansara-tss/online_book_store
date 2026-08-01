package com.online_bookstore.app.exceptions;

public class AuthorNotFoundException extends ResourceNotFoundException {
    public AuthorNotFoundException(Long id) {
        super("Author not found with id: " + id);
    }
}
