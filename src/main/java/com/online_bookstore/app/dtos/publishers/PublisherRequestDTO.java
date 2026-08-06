package com.online_bookstore.app.dtos.publishers;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class PublisherRequestDTO {
    @NotBlank(message = "Publisher name is required.")
    private String name;
}
