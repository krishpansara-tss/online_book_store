package com.online_bookstore.app.services.interfaces;


import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.reviews.ReviewRequestDTO;
import com.online_bookstore.app.dtos.reviews.ReviewResponseDTO;
import com.online_bookstore.app.models.Review;


public interface IReviewService {
    ReviewResponseDTO addNewReview(Long userId, Long bookId,ReviewRequestDTO dto);
    PageResponse<ReviewResponseDTO> getAllReviewsOfTheBook(Long bookId, Integer page, Integer size);
    PageResponse<ReviewResponseDTO> getAllReviewsOfTheUser(Long userId, Integer page, Integer size);
    ReviewResponseDTO getReviewById(Long reviewId);
    void deleteReview(Long reviewId);
    void activeReview(Long reviewId);
    Review getReviewEntityById(Long reviewId);
    PageResponse<ReviewResponseDTO> getAllActiveReview(Integer page, Integer size);
}
