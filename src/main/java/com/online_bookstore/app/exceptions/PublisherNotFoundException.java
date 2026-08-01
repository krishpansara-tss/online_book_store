package com.online_bookstore.app.exceptions;

public class PublisherNotFoundException extends ResourceNotFoundException {
    public PublisherNotFoundException(Long id) {
        super("Publisher not found with id: " + id);
    }
}
