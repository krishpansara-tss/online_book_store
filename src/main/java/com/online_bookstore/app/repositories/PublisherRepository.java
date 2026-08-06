package com.online_bookstore.app.repositories;

import com.online_bookstore.app.models.Publisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    boolean existsByNameIgnoreCase(String name);
    Page<Publisher> findByNameContainingIgnoreCase(String name, Pageable pageable);
    Page<Publisher> findByIsActiveTrue(Pageable pageable);
}
