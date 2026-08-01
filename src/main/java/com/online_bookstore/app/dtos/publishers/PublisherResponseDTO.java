package com.online_bookstore.app.dtos.publishers;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class PublisherResponseDTO {
    private Long publisherId;
    private String name;
}
