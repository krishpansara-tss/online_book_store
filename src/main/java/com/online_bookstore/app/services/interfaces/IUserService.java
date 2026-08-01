package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.users.UserRequestDTO;
import com.online_bookstore.app.dtos.users.UserResponseDTO;

public interface IUserService {
    UserResponseDTO registerUser(UserRequestDTO dto);
    PageResponse<UserResponseDTO> getAllUsers(Integer page, Integer size);
    UserResponseDTO getUserById(Long userId);
}
