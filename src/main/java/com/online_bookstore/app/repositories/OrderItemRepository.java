package com.online_bookstore.app.repositories;

import com.online_bookstore.app.models.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("""
            SELECT COUNT(oi) > 0
            FROM OrderItem oi
            WHERE oi.order.user.userId = :userId
            AND oi.book.bookId = :bookId
            """)
    boolean hasUserPurchasedBook(Long userId, Long bookId);

    @Query("""
        SELECT oi.orderItemId, b.title, oi.quantity, oi.priceAtPurchase
        FROM OrderItem oi
        LEFT JOIN oi.book b
        WHERE oi.order.orderId = :orderId
        """)
    Page<Object[]> findAllItemsByOrderId(Long orderId, Pageable pageable);
}
