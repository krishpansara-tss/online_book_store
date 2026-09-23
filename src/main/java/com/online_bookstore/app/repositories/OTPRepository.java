package com.online_bookstore.app.repositories;

import com.online_bookstore.app.models.OTP;
import com.online_bookstore.app.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    User findByUserEmail(String user);
    Long countByUserUserIdAndCreatedAtAfter(Long userId, LocalDateTime windowStart);
    OTP findTopByUserEmailAndUsedFalseOrderByCreatedAtDesc(String email);
}
