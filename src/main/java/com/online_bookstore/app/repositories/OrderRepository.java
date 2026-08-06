package com.online_bookstore.app.repositories;

import com.online_bookstore.app.dtos.orders.OrderBasicInformationDTO;
import com.online_bookstore.app.models.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByIsActiveTrue(Pageable pageable);

    @Query("""
            SELECT new com.online_bookstore.app.dtos.orders.OrderBasicInformationDTO(
                o.orderId, u.userId, u.name, o.totalAmount, o.orderAddress
            )FROM Order o
            LEFT JOIN o.user u
    """)
    Page<OrderBasicInformationDTO> getBasicInformationOfOrders(Pageable pageable);

    @Query("""
            SELECT new com.online_bookstore.app.dtos.orders.OrderBasicInformationDTO(
                o.orderId, u.userId, u.name, o.totalAmount, o.orderAddress
            )FROM Order o
            LEFT JOIN o.user u
            WHERE o.isActive = true
    """)
    Page<OrderBasicInformationDTO> getBasicInformationOfActiveOrders(Pageable pageable);
}
