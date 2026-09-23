package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.models.User;
import com.online_bookstore.app.services.interfaces.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationProcessor {
    private final Map<String, INotificationService> notificationProcessors;

    public INotificationService getProcessor(String type ){
        INotificationService processor;
        if(type == null){
            processor = notificationProcessors.get("sms");
        }else{
            processor = notificationProcessors.get(type);
        }

        if(processor == null){
            throw new RuntimeException("Method not found");
        }
        return processor;
    }

    public void sendOtp(String type, User user){
        INotificationService service = getProcessor(type);

        service.sendOtp(user);

        System.out.println("user : " + user.getUserId());
    }

    public void verifyOtp(String type, String email, String otp){
        INotificationService service = getProcessor(type);

        service.verifyOtp(email, otp);

        System.out.println("otp : " + otp);
    }
}
