package com.online_bookstore.app.dtos.books;

import com.online_bookstore.app.dtos.authors.AuthorResponseDTO;
import com.online_bookstore.app.dtos.categories.CategoryResponseDTO;
import com.online_bookstore.app.dtos.publishers.PublisherResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class BookBasicInformationResponseDTO {
    private Long bookId;
    private String title;
    private String ISBN;
    private Double price;
    private Long stock;
    private Double ratings;
}
