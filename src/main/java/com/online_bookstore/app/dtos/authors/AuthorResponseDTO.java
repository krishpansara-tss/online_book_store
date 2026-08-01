package com.online_bookstore.app.dtos.authors;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AuthorResponseDTO {
    private Long authorId;
    private String name;
}
