package com.online_bookstore.app.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class UserBasicInformationResponseDTO {
    private Long userId;
    private String email;
    private String name;
}
