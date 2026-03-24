package org.example.tuan3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.example.tuan3.dto.ApiResponse;
import org.example.tuan3.entity.Task;
import org.example.tuan3.entity.User;
import org.example.tuan3.enums.TaskStatus;
import org.example.tuan3.repository.UserRepository;
import org.example.tuan3.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Tag(name = "Quản lý Công việc", description = "Các API dùng để thêm, sửa, giao việc")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/{userId}")
    public ApiResponse<List<Task>> getTasksByUser(@PathVariable String userId) {
        return ApiResponse.<List<Task>>builder()
                .code(1000)
                .result(taskService.getTasksByUser(userId))
                .build();
    }

    @GetMapping("/project/{projectId}")
    public ApiResponse<List<Task>> getTasksByProject(@PathVariable String projectId) {
        return ApiResponse.<List<Task>>builder()
                .code(1000)
                .result(taskService.getTasksByProject(projectId))
                .build();
    }

    @PostMapping
    @Operation(summary = "Tạo công việc mới", description = "API này dùng để tạo một Task mới và gắn vào Project")
    public ApiResponse<Task> createTask(@Valid @RequestBody Task task) {
        return ApiResponse.<Task>builder()
                .code(1000)
                .message("Tạo công việc thành công!")
                .result(taskService.createTask(task))
                .build();
    }

    @PutMapping("/{taskId}/assign/{userId}")
    @Operation(summary = "Giao việc cho nhân viên", description = "Gán một User vào một Task cụ thể")
    public ApiResponse<Task> assignTask(@PathVariable String taskId, @PathVariable String userId) {
        return ApiResponse.<Task>builder()
                .code(1000)
                .message("Giao việc thành công!")
                .result(taskService.assignTask(taskId, userId))
                .build();
    }

    @PutMapping("/{taskId}/status/{newStatus}")
    public ApiResponse<Task> updateStatus(@PathVariable String taskId, @PathVariable TaskStatus newStatus) {
        return ApiResponse.<Task>builder()
                .code(1000)
                .message("Cập nhật trạng thái thành công!")
                .result(taskService.updateTaskStatus(taskId, newStatus))
                .build();
    }


    @GetMapping("/my-tasks")
    public ApiResponse<List<Task>> getMyTasks() {
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();


        User currentUser = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy User"));


        return ApiResponse.<List<Task>>builder()
                .code(1000)
                .message("Lấy danh sách công việc cá nhân thành công!")
                .result(taskService.getTasksByUser(currentUser.getId()))
                .build();
    }

}
