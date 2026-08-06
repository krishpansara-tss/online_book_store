package com.online_bookstore.app.dtos.reviews;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class ReviewResponseDTO {
    private Long reviewId;
    private String comment;
    private Integer rating;
//
//    private BookResponseDTO book;
//    private UserResponseDTO user;
}
