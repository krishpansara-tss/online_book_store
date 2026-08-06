package com.online_bookstore.app.repositories;

import com.online_bookstore.app.models.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
           SELECT AVG(r.rating)
           FROM Review r
           WHERE r.book.bookId = :bookId
           """)
    Double findAverageRatingByBookId(@Param("bookId") Long bookId);

    Page<Review> findByIsActiveTrue(Pageable pageable);
    Page<Review> findByUserUserId(Long userId, Pageable pageable);
    Page<Review> findByBookBookId(Long userId, Pageable pageable);

    Optional<Review> findByUserUserIdAndBookBookId(Long userId, Long bookId);

    boolean existsByUserUserIdAndBookBookId(Long userId, Long bookId);
}
