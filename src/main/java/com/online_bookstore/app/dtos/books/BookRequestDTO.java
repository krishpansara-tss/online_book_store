package com.online_bookstore.app.dtos.books;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Data
public class BookRequestDTO {
    private String title;
    private String ISBN;
    private Double price;
    private Long stock;
    private Double ratings;
    private Long categoryId;
    private Long publisherId;

    private List<Long> authorIds;
}
