package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.users.UserBasicInformationResponseDTO;
import com.online_bookstore.app.dtos.users.UserRequestDTO;
import com.online_bookstore.app.dtos.users.UserResponseDTO;
import com.online_bookstore.app.models.User;

public interface IUserService {
    UserResponseDTO registerUser(UserRequestDTO dto);
    UserResponseDTO getUserByName(String name);
    UserResponseDTO getUserById(Long userId);

    User getUserEntityById(Long userId);

    void deleteUser(Long userId);
    void activeUser(Long userId);

    PageResponse<UserResponseDTO> searchUserByNameContaining(String key, Integer page, Integer size, String sortBy, String  direction);
    PageResponse<UserBasicInformationResponseDTO> getAllActiveUser(Integer page, Integer size, String sortBy, String  direction);
    PageResponse<UserBasicInformationResponseDTO> getAllUsers(Integer page, Integer size, String sortBy, String  direction);

}
