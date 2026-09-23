package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.models.User;

public interface INotificationService {

    void sendOtp(User user);
    void verifyOtp(String email, String otp);
}
