package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.users.UserBasicInformationResponseDTO;
import com.online_bookstore.app.dtos.users.UserRequestDTO;
import com.online_bookstore.app.dtos.users.UserResponseDTO;
import com.online_bookstore.app.exceptions.DuplicateResourceException;
import com.online_bookstore.app.exceptions.InvalidOperationException;
import com.online_bookstore.app.exceptions.UserNotFoundException;
import com.online_bookstore.app.mappers.UserMapper;
import com.online_bookstore.app.models.User;
import com.online_bookstore.app.repositories.UserRepository;
import com.online_bookstore.app.services.interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;


@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserResponseDTO registerUser(UserRequestDTO dto){
        User user = userMapper.toEntity(dto);
        if (user.getProfile() != null) {
            user.getProfile().setUser(user);
        }

        if(userRepository.existsByEmailIgnoreCase(dto.getEmail())){
            logger.error("User having the email {} already exists.", dto.getEmail());
            throw new DuplicateResourceException("User already exists having the email.");
        }

        User added_user = userRepository.save(user);
        logger.info("User having ID: {} added Successfully.", added_user.getUserId());
        return userMapper.toResponse(added_user);
    }

    @Override
    public PageResponse<UserBasicInformationResponseDTO> getAllUsers(Integer page, Integer size, String sortBy, String  direction){
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserBasicInformationResponseDTO> userPage = userRepository.getAllUserBasicInformation(pageable);
        List<UserBasicInformationResponseDTO> content = userPage.stream().toList();

        return PageResponse.<UserBasicInformationResponseDTO>builder()
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
                .orElseThrow(() -> {
                    logger.error("User having ID: {} doesn't exists.", userId);
                    return new UserNotFoundException(userId);
                });

        return userMapper.toResponse(user);
    }

    @Override
    public User getUserEntityById(Long userId) {
         return userRepository.findById(userId)
                 .orElseThrow(() -> {
                     logger.error("User having ID: {} doesn't exists.", userId);
                     return new UserNotFoundException(userId);
                 });
    }

    @Override
    public PageResponse<UserBasicInformationResponseDTO> getAllActiveUser(Integer page, Integer size, String sortBy, String  direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<UserBasicInformationResponseDTO> userPage = userRepository.getActiveUserBasicInformation(pageable);

        List<UserBasicInformationResponseDTO> content = userPage.stream().toList();


        return PageResponse.<UserBasicInformationResponseDTO>builder()
                .content(content).
                page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .build();
    }

    @Override
    public void deleteUser(Long userId) {
        User user = getUserEntityById(userId);

        if(!user.isActive()){
            logger.error("User having ID: {} is already deleted.", userId);
            throw new InvalidOperationException("User is already deleted");
        }

        user.setActive(false);
        logger.info("User having ID: {} is deleted successfully.", userId);
        userRepository.save(user);
    }

    @Override
    public void activeUser(Long userId) {
        User user = getUserEntityById(userId);
        if(user.isActive()){
            logger.error("User having ID: {} is already in active state.", userId);
            throw new InvalidOperationException("User is already activate");
        }
        logger.info("User having ID: {} is activated successfully.", userId);
        user.setActive(true);
        userRepository.save(user);
    }

    @Override
    public PageResponse<UserResponseDTO> searchUserByNameContaining(String key, Integer page, Integer size, String sortBy, String  direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<User> userPage = userRepository.findByNameContainingIgnoreCase(key, pageable);

        List<UserResponseDTO> content = userPage.stream()
                .map(userMapper::toResponse)
                .toList();

        return PageResponse.<UserResponseDTO>builder()
                .content(content).
                page(userPage.getNumber())
                .size(userPage.getSize())
                .totalElements(userPage.getTotalElements())
                .totalPages(userPage.getTotalPages())
                .last(userPage.isLast())
                .build();
    }

    @Override
    public UserResponseDTO getUserByName(String name) {
        User user = userRepository.findByName(name);

        return userMapper.toResponse(user);
    }
}
