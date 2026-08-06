package com.online_bookstore.app.dtos.books;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class BookUpdateDTO {
    private String title;
    private String ISBN;
    private Double price;
}
