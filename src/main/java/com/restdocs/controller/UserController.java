package com.restdocs.controller;

import com.restdocs.request.UserRequest;
import com.restdocs.response.UserResponse;
import com.restdocs.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/user")
    public UserResponse createUser(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/user/{userId}")
    public UserResponse findUserById(@PathVariable Long userId) {
        return userService.findUserById(userId);
    }

}
