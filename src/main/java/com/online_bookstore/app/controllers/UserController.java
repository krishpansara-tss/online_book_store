package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.users.UserBasicInformationResponseDTO;
import com.online_bookstore.app.dtos.users.UserRequestDTO;
import com.online_bookstore.app.dtos.users.UserResponseDTO;
import com.online_bookstore.app.services.implemantation.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO dto){
        UserResponseDTO user = userService.registerUser(dto);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<UserBasicInformationResponseDTO>> getAllUsers(@RequestParam(defaultValue = "0") Integer page,
                                                                                     @RequestParam(defaultValue = "5") Integer size,
                                                                                     @RequestParam(defaultValue = "name") String sortBy,
                                                                                     @RequestParam(defaultValue = "asc") String direction){
        PageResponse<UserBasicInformationResponseDTO> users = userService.getAllUsers(page, size, sortBy, direction);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long user_id){
        UserResponseDTO user = userService.getUserById(user_id);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long user_id){
        userService.deleteUser(user_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/active/{user_id}")
    public ResponseEntity<Void> activateUserById(@PathVariable Long user_id){
        userService.activeUser(user_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all/active")
    public ResponseEntity<PageResponse<UserBasicInformationResponseDTO>> getAllActiveUsers(@RequestParam(defaultValue = "0") Integer page,
                                                                           @RequestParam(defaultValue = "5") Integer size,
                                                                           @RequestParam(defaultValue = "name") String sortBy,
                                                                           @RequestParam(defaultValue = "asc") String direction){
        PageResponse<UserBasicInformationResponseDTO> users = userService.getAllActiveUser(page, size, sortBy, direction);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<UserResponseDTO>> getUserByName(@RequestParam(defaultValue = "") String name,
                                                                       @RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "5") Integer size,
                                                                       @RequestParam(defaultValue = "name") String sortBy,
                                                                       @RequestParam(defaultValue = "asc") String direction){
        PageResponse<UserResponseDTO> user = userService.searchUserByNameContaining(name, page, size, sortBy, direction);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
