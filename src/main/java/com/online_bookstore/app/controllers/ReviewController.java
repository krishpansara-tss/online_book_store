package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.reviews.ReviewRequestDTO;
import com.online_bookstore.app.dtos.reviews.ReviewResponseDTO;
import com.online_bookstore.app.services.implemantation.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping("/user/{user_id}/book/{book_id}")
    public ResponseEntity<ReviewResponseDTO> giveReview(@PathVariable Long user_id,
                                                        @PathVariable Long book_id,
                                                        @Valid @RequestBody ReviewRequestDTO dto){
        System.out.println(dto);
        ReviewResponseDTO response = reviewService.addNewReview(user_id, book_id, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{review_id}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long review_id){
        ReviewResponseDTO response = reviewService.getReviewById(review_id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/book/{book_id}")
    public ResponseEntity<PageResponse<ReviewResponseDTO>> getAllReviewOfBook(@PathVariable Long book_id,
                                                                      @RequestParam(defaultValue = "0") Integer page,
                                                                      @RequestParam(defaultValue = "5") Integer size){
        PageResponse<ReviewResponseDTO> response = reviewService.getAllReviewsOfTheBook(book_id, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/active")
    public ResponseEntity<PageResponse<ReviewResponseDTO>> getAllActiveReview(@RequestParam(defaultValue = "0") Integer page,
                                                              @RequestParam(defaultValue = "5") Integer size){
        PageResponse<ReviewResponseDTO> response = reviewService.getAllActiveReview(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<PageResponse<ReviewResponseDTO>> getAllReviewGivenByUser(@PathVariable Long user_id,
                                                                           @RequestParam(defaultValue = "0") Integer page,
                                                                           @RequestParam(defaultValue = "5") Integer size){
        PageResponse<ReviewResponseDTO> response = reviewService.getAllReviewsOfTheUser(user_id, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{review_id}")
    public ResponseEntity<Void> deleteReviewId(@PathVariable Long review_id){
        reviewService.deleteReview(review_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/active/{review_id}")
    public ResponseEntity<Void> activeReviewById(@PathVariable Long review_id){
        reviewService.activeReview(review_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
