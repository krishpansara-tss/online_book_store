package com.online_bookstore.app.dtos.users;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Data
public class UserProfileResponseDTO {
    private Long profileId;
    private String address;
    private String phoneNumber;
    private LocalDate dateOfBirth;
}
