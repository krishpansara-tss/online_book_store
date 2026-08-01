package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.users.UserRequestDTO;
import com.online_bookstore.app.dtos.users.UserResponseDTO;
import com.online_bookstore.app.exceptions.UserNotFoundException;
import com.online_bookstore.app.mappers.UserMapper;
import com.online_bookstore.app.models.User;
import com.online_bookstore.app.repositories.UserRepository;
import com.online_bookstore.app.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserResponseDTO registerUser(UserRequestDTO dto){
        User user = userMapper.toEntity(dto);
        if (user.getProfile() != null) {
            user.getProfile().setUser(user);
        }

        User added_user = userRepository.save(user);
        return userMapper.toResponse(added_user);
    }

    public PageResponse<UserResponseDTO> getAllUsers(Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);
        Page<User> userPage = userRepository.findAll(pageable);

        List<UserResponseDTO> content = userPage
                .getContent()
                .stream()
                .map(userMapper::toResponse)
                .toList();

        return PageResponse.<UserResponseDTO>builder()
                .content(content)
                .page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .build();
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        return userMapper.toResponse(user);
    }
}
