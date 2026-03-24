package org.example.tuan3.controller;

import org.example.tuan3.dto.ApiResponse;
import org.example.tuan3.dto.UserCreationRequest;
import org.example.tuan3.entity.User;
import org.example.tuan3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return ApiResponse.<List<User>>builder()
                .code(1000) // Mã thành công
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable String id) {
        return ApiResponse.<User>builder()
                .code(1000)
                .result(userService.getUserById(id))
                .build();
    }

    @PostMapping("/register")
    public ApiResponse<User> register(@RequestBody UserCreationRequest request) {
        return ApiResponse.<User>builder()
                .code(1000)
                .message("Đăng ký tài khoản thành công!")
                .result(userService.createUser(request))
                .build();
    }
}
