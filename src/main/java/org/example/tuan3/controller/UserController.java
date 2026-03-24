package org.example.tuan3.controller;

import org.example.tuan3.dto.ApiResponse;
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

    // 1. Lấy tất cả người dùng
    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return ApiResponse.<List<User>>builder()
                .code(1000) // Mã thành công
                .result(userService.getAllUsers())
                .build();
    }

    // 2. Lấy người dùng theo ID
    @GetMapping("/{id}")
    public ApiResponse<User> getUserById(@PathVariable String id) {
        // Lưu ý: Logic check null nên để ở Service và ném Exception
        // để GlobalExceptionHandler xử lý sẽ chuyên nghiệp hơn.
        return ApiResponse.<User>builder()
                .code(1000)
                .result(userService.getUserById(id))
                .build();
    }

    // 3. Tạo người dùng mới
    @PostMapping
    public ApiResponse<User> createUser(@RequestBody User user) {
        return ApiResponse.<User>builder()
                .code(1000)
                .message("Tạo người dùng thành công!")
                .result(userService.createUser(user))
                .build();
    }
}
