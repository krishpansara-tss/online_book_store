package com.online_bookstore.app.dtos.books;

import com.online_bookstore.app.dtos.authors.AuthorResponseDTO;
import com.online_bookstore.app.dtos.categories.CategoryResponseDTO;
import com.online_bookstore.app.dtos.publishers.PublisherResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class BookResponseDTO {
    private Long bookId;
    private String title;
    private String ISBN;
    private Double price;
    private Long stock;
    private Double ratings;

    private CategoryResponseDTO category;
    private PublisherResponseDTO publisher;
    private List<AuthorResponseDTO> authors;
}
