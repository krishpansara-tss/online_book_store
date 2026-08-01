package com.online_bookstore.app.exceptions;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException(Long id) {
        super("User not found with id: " + id);
    }
}
