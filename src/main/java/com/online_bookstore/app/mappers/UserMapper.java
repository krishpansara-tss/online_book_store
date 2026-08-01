package com.online_bookstore.app.mappers;

import com.online_bookstore.app.dtos.users.UserRequestDTO;
import com.online_bookstore.app.dtos.users.UserResponseDTO;
import com.online_bookstore.app.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserRequestDTO dto);
    UserResponseDTO toResponse(User user);
}
