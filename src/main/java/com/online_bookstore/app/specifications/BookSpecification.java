package com.online_bookstore.app.specifications;

import com.online_bookstore.app.models.Author;
import com.online_bookstore.app.models.Book;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {

    public static Specification<Book> hasTitle(String title){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }
    public static Specification<Book> hasCategory(String categoryName){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("category").get("name")),
                        "%" + categoryName + "%"
                );
    }

    public static Specification<Book> hasAuthor(String author){
        return (root, query, criteriaBuilder) -> {
            Join<Book, Author> join = root.join("authors");
            return criteriaBuilder.like(
                    criteriaBuilder.lower(join.get("name")),
                    "%" + author.toLowerCase() + "%"
            );
        };
    }

    public static Specification<Book> hasIsbn(String isbn){
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(
                        root.get("isbn"),
                        isbn
                );
    }

    public static Specification<Book> hasPublisher(String publisherName){
        return (root, query, criteriaBuilder) ->

                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("publishers").get("name")),
                        "%" + publisherName + "%"
                );
    }

    public static Specification<Book> minPrice(Double minPrice){
        return  (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"),
                        minPrice
                );
    }

    public static Specification<Book> maxPrice(Double maxPrice){
        return  (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"),
                        maxPrice
                );
    }

    public static Specification<Book> minRating(Double minRating){
        return  (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(
                        root.get("ratings"),
                        minRating
                );
    }
}
