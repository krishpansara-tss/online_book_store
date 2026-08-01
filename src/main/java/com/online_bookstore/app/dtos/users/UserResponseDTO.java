package com.online_bookstore.app.dtos.users;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class UserResponseDTO {
    private Long userId;
    private String name;
    private String email;

    private UserProfileResponseDTO profile;
}
