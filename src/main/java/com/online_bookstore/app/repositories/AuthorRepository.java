package com.online_bookstore.app.repositories;

import com.online_bookstore.app.models.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    Author findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
    Page<Author> findByIsActiveTrue(Pageable pageable);
}
