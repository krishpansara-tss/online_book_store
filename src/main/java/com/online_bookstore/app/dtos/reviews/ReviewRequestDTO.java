package com.online_bookstore.app.dtos.reviews;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;

@RequiredArgsConstructor
@Data
public class ReviewRequestDTO {
    @NotBlank(message = "Review Comment name is required.")
    private String comment;

    @Range(min = 0, max = 5, message = "Rating must between 0 to 5.")
    private Integer rating;
}
