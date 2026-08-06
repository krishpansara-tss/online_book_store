package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.reviews.ReviewRequestDTO;
import com.online_bookstore.app.dtos.reviews.ReviewResponseDTO;
import com.online_bookstore.app.exceptions.InvalidOperationException;
import com.online_bookstore.app.exceptions.ReviewNotFoundException;
import com.online_bookstore.app.mappers.ReviewMapper;
import com.online_bookstore.app.models.Book;
import com.online_bookstore.app.models.Review;
import com.online_bookstore.app.models.User;
import com.online_bookstore.app.repositories.ReviewRepository;
import com.online_bookstore.app.services.interfaces.IReviewService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ReviewService implements IReviewService {
    private final ReviewRepository reviewRepository;

    private final UserService userService;
    private final OrderItemService orderItemService;
    private final BookService bookService;

    private final ReviewMapper reviewMapper;

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Override
    public ReviewResponseDTO addNewReview(Long userId, Long bookId, ReviewRequestDTO dto) {
        User user = userService.getUserEntityById(userId);
        Book book = bookService.getBookEntityById(bookId);

        Optional<Review> existingReview = reviewRepository.findByUserUserIdAndBookBookId(userId, bookId);
        Review review;
        if(existingReview.isPresent()){
            review = existingReview.get();

            review.setComment(dto.getComment());
            review.setRating(dto.getRating());

        }else {

            boolean purchased = orderItemService.hasUserPurchasedBook(userId, bookId);
            if (!purchased) {
                logger.error("To give review You (having ID: {}) first have to Purchase the book (having ID: {})", userId, bookId);
                throw new InvalidOperationException("To give review You first have to Purchase the book");
            }
            review = reviewMapper.toEntity(dto);
            review.setUser(user);
            review.setBook(book);
        }

        Review saved_review = reviewRepository.save(review);
        Double updated_rating = getAverageRatingByBookId(bookId);
        bookService.updateBookRating(book, updated_rating);

        logger.info("Review added successfully having ID :{}", saved_review.getReviewId());

        return reviewMapper.toResponse(saved_review);
    }

    @Override
    public PageResponse<ReviewResponseDTO> getAllReviewsOfTheBook(Long bookId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Review> reviewPage = reviewRepository.findByBookBookId(bookId, pageable);

        List<ReviewResponseDTO> content = reviewPage.stream()
                .map(reviewMapper::toResponse)
                .toList();

        return PageResponse.<ReviewResponseDTO>builder()
                .content(content).
                page(reviewPage.getNumber())
                .size(reviewPage.getSize())
                .totalElements(reviewPage.getTotalElements())
                .totalPages(reviewPage.getTotalPages())
                .last(reviewPage.isLast())
                .build();
    }

    @Override
    public PageResponse<ReviewResponseDTO> getAllReviewsOfTheUser(Long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Review> reviewPage = reviewRepository.findByUserUserId(userId, pageable);

        List<ReviewResponseDTO> content = reviewPage.stream()
                .map(reviewMapper::toResponse)
                .toList();

        return PageResponse.<ReviewResponseDTO>builder()
                .content(content).
                page(reviewPage.getNumber())
                .size(reviewPage.getSize())
                .totalElements(reviewPage.getTotalElements())
                .totalPages(reviewPage.getTotalPages())
                .last(reviewPage.isLast())
                .build();
    }

    @Override
    public ReviewResponseDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> {
                    logger.error("Review having ID: {} doesn't exists.", reviewId);
                    return new ReviewNotFoundException(reviewId);
                }
        );

        return reviewMapper.toResponse(review);
    }

    public Double getAverageRatingByBookId(Long bookId){
        return reviewRepository.findAverageRatingByBookId(bookId);
    }

    @Override
    public PageResponse<ReviewResponseDTO> getAllActiveReview(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Review> reviewPage = reviewRepository.findByIsActiveTrue(pageable);

        List<ReviewResponseDTO> content = reviewPage.stream()
                .map(reviewMapper::toResponse)
                .toList();


        return PageResponse.<ReviewResponseDTO>builder()
                .content(content).
                page(reviewPage.getNumber())
                .size(reviewPage.getSize())
                .totalElements(reviewPage.getTotalElements())
                .totalPages(reviewPage.getTotalPages())
                .last(reviewPage.isLast())
                .build();
    }

    @Override
    public void deleteReview(Long reviewId) {
        Review review = getReviewEntityById(reviewId);
        if(!review.isActive()){
            logger.error("Review having ID: {} is already deleted.", reviewId);
            throw new InvalidOperationException("Review is already deleted");
        }
        logger.info("Review having ID: {} is deleted successfully.", reviewId);

        review.setActive(false);
        reviewRepository.save(review);

    }

    @Override
    public void activeReview(Long reviewId) {
        Review review = getReviewEntityById(reviewId);
        if(review.isActive()){
            logger.error("Review having ID: {} is already active state.", reviewId);
            throw new InvalidOperationException("Review is already activate");
        }

        review.setActive(true);
        logger.info("Review having ID: {} is activated successfully.", reviewId);

        reviewRepository.save(review);
    }

    @Override
    public Review getReviewEntityById(Long reviewId){
        return reviewRepository.findById(reviewId).orElseThrow(
                () -> {
                    logger.error("Review having ID: {} doesn't exists.", reviewId);
                    return new ReviewNotFoundException(reviewId);
                }
        );
    }

}
