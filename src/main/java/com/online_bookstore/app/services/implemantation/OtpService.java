package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.services.interfaces.IOtpService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class OtpService implements IOtpService {
    @Override
    public String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
