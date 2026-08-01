package com.online_bookstore.app.exceptions;

public class BookNotFoundException extends ResourceNotFoundException {
    public BookNotFoundException(Long id) {
        super("Book not found with id: " + id);
    }
}
