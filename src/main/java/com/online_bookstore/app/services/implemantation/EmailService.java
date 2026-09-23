package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.models.OTP;
import com.online_bookstore.app.models.User;
import com.online_bookstore.app.repositories.OTPRepository;
import com.online_bookstore.app.repositories.UserRepository;
import com.online_bookstore.app.services.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("email")
@RequiredArgsConstructor
public class EmailService implements INotificationService {
    private final OTPRepository otpRepository;

    private final UserRepository userRepository;

    private final JavaMailSender mailSender;
    private final OtpService otpService;

    public void sendMessage(String to, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setText(message);

        mailSender.send(mailMessage);

        System.out.println("Receiver : " + to + " | Message (Sent ON Email) : " + message);
    }

    @Override
    public void verifyOtp(String email, String otp) {
        User user = userRepository.findByEmailIgnoreCase(email);
        if(user == null){
            throw new RuntimeException("User not found ");
        }

        OTP otpObject = otpRepository.findTopByUserEmailAndUsedFalseOrderByCreatedAtDesc(email);

        if(otpObject == null){
            throw new RuntimeException("OTP not sent to your account or you have already used it");
        }

        if(otpObject.getExpiryTime().isBefore(LocalDateTime.now())){
            throw new RuntimeException("OTP has been expired");
        }

        if(otpObject.isUsed()){
            throw new RuntimeException("OTP have already used the otp");
        }

        if(!otpObject.getVerificationCode().equals(otp)){
            throw new RuntimeException("OTP didn't matched");
        }else{
            user.setVerified(true);
            otpObject.setUsed(true);
            System.out.println("OTP MATCHED");
            otpRepository.save(otpObject);
        }
    }

    public void sendOtp(User user) {
        LocalDateTime windowStart = LocalDateTime.now().minusMinutes(5);

        Long attempts = otpRepository.countByUserUserIdAndCreatedAtAfter(user.getUserId(), windowStart);

        if (attempts >= 3) {
            throw new RuntimeException("Too many OTP requests. Try again later.");
        }

        String otp = otpService.generateOtp();

        sendMessage(user.getEmail(), otp);

        OTP otpObject = new OTP();
        otpObject.setUser(user);
        otpObject.setVerificationCode(otp);
        otpObject.setExpiryTime(LocalDateTime.now().plusMinutes(2));
        otpObject.setUsed(false);

        otpRepository.save(otpObject);
        System.out.println("done");
    }
}
