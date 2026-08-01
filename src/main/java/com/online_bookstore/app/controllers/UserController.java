package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.users.UserRequestDTO;
import com.online_bookstore.app.dtos.users.UserResponseDTO;
import com.online_bookstore.app.services.implemantation.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@RequestBody UserRequestDTO dto){
        UserResponseDTO user = userService.registerUser(dto);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<UserResponseDTO>> getAllUsers(@RequestParam(defaultValue = "0") Integer page,
                                                                     @RequestParam(defaultValue = "5") Integer size){
        PageResponse<UserResponseDTO> users = userService.getAllUsers(page, size);

        return new ResponseEntity<>(users, HttpStatus.FOUND);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long user_id){
        UserResponseDTO user = userService.getUserById(user_id);

        return new ResponseEntity<>(user, HttpStatus.FOUND);
    }
}
