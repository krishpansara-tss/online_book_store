package com.online_bookstore.app.repositories;

import com.online_bookstore.app.dtos.books.BookBasicInformationResponseDTO;
import com.online_bookstore.app.models.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
    boolean existsByTitleIgnoreCase(String name);
    Page<Book> findByIsActiveTrue(Pageable pageable);

    @Query("""
            SELECT DISTINCT b
            FROM Book b
            LEFT JOIN b.category
            LEFT JOIN b.publisher
            LEFT JOIN b.authors
            WHERE b.bookId = :bookId
        """)
    Book findBookDetailsById(Long bookId);

    @Query("""
        SELECT new com.online_bookstore.app.dtos.books.BookBasicInformationResponseDTO(
            b.bookId, b.title, b.ISBN, b.price, b.stock, b.ratings
        )
        FROM Book b
    """)
    Page<BookBasicInformationResponseDTO> getAllBookBasicInformation(Pageable pageable);

    @Query("""
        SELECT new com.online_bookstore.app.dtos.books.BookBasicInformationResponseDTO(
            b.bookId, b.title, b.ISBN, b.price, b.stock, b.ratings
        )
        FROM Book b
        WHERE b.isActive = true
    """)
    Page<BookBasicInformationResponseDTO> getAllActiveBookBasicInformation(Pageable pageable);
}
