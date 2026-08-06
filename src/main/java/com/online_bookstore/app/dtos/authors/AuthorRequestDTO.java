package com.online_bookstore.app.dtos.authors;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class AuthorRequestDTO {
    @NotBlank(message = "Author name is required.")
    private String name;
}
