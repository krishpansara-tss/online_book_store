package com.online_bookstore.app.exceptions;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(Long id) {
        super("Order not found with id: " + id);
    }
}
