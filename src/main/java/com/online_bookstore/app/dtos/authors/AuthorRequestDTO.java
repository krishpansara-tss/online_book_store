package com.online_bookstore.app.dtos.authors;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AuthorRequestDTO {
    private String name;
}
