package com.online_bookstore.app.mappers;

import com.online_bookstore.app.dtos.reviews.ReviewRequestDTO;
import com.online_bookstore.app.dtos.reviews.ReviewResponseDTO;
import com.online_bookstore.app.models.Review;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    Review toEntity(ReviewRequestDTO dto);
    ReviewResponseDTO toResponse(Review review);
}
