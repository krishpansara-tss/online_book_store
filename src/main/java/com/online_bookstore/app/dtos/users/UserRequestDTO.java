package com.online_bookstore.app.dtos.users;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Data
public class UserRequestDTO {
    private String name;
    private String email;
    private String password;

    private UserProfileRequestDTO profile;
}
