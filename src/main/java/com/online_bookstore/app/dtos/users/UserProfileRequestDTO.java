package com.online_bookstore.app.dtos.users;

import com.online_bookstore.app.validators.annotations.MinimumAge;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@RequiredArgsConstructor
@Data
public class UserProfileRequestDTO {
    @NotBlank(message = "User Address is required.")
    private String address;

    @NotBlank(message = "User Phone Number is required.")
    private String phoneNumber;

    @NotNull(message = "User Date of Birth is required.")
    @Past(message = "Date of birth must be in the past.")
    @MinimumAge(value = 10, message = "User must be at least 18 years old.")
    private LocalDate dateOfBirth;
}
