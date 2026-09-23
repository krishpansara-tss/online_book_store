package com.online_bookstore.app.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "otp")
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otpId;
    private String verificationCode;

    @CreationTimestamp
    private LocalDateTime createdAt;
    private LocalDateTime expiryTime;

    private boolean used = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
