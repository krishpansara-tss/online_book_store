package com.online_bookstore.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "reviews",
    uniqueConstraints = {
            @UniqueConstraint(
                    name = "uk_review_user_book",
                    columnNames = {"user_id", "book_id"}
            )
    }
)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;
    private String comment;
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private boolean isActive = true;
}
