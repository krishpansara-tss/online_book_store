package com.online_bookstore.app.dtos.books;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class BookSearchRequestDTO {

    private String title;
    private String isbn;
    private String category;
    private String author;
    private String publisher;
    private Double minPrice;
    private Double maxPrice;
    private Double minRating;
    private Boolean inStock;

}
