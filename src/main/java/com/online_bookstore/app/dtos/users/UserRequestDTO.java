package com.online_bookstore.app.dtos.users;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class UserRequestDTO {
    @NotBlank(message = "User Name is required.")
    private String name;

    @NotBlank(message = "User Email Address is required.")
    @Email(message = "Please enter Valid Email")
    @Size(max = 100, message = "Email cannot exceed 100 characters.")
    private String email;

    @NotBlank(message = "Password is required.")
//    @Size(min = 8, max = 20,
//            message = "Password must be between 8 and 20 characters.")
//    @Pattern(
//            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
//            message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character."
//    )
    private String password;

    @Valid
    private UserProfileRequestDTO profile;
}
