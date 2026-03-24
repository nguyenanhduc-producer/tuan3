package org.example.tuan3.controller;

import jakarta.validation.Valid;
import org.example.tuan3.dto.ApiResponse;
import org.example.tuan3.entity.Task;
import org.example.tuan3.enums.TaskStatus;
import org.example.tuan3.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    // 1. Lấy danh sách Task theo User
    @GetMapping("/user/{userId}")
    public ApiResponse<List<Task>> getTasksByUser(@PathVariable String userId) {
        return ApiResponse.<List<Task>>builder()
                .code(1000)
                .result(taskService.getTasksByUser(userId))
                .build();
    }

    // 2. Lấy danh sách Task theo Project
    @GetMapping("/project/{projectId}")
    public ApiResponse<List<Task>> getTasksByProject(@PathVariable String projectId) {
        return ApiResponse.<List<Task>>builder()
                .code(1000)
                .result(taskService.getTasksByProject(projectId))
                .build();
    }

    // 3. Tạo Task mới (Có Validation)
    @PostMapping
    public ApiResponse<Task> createTask(@Valid @RequestBody Task task) {
        return ApiResponse.<Task>builder()
                .code(1000)
                .message("Tạo công việc thành công!")
                .result(taskService.createTask(task))
                .build();
    }

    // 4. Giao việc (Assign Task)
    @PutMapping("/{taskId}/assign/{userId}")
    public ApiResponse<Task> assignTask(@PathVariable String taskId, @PathVariable String userId) {
        return ApiResponse.<Task>builder()
                .code(1000)
                .message("Giao việc thành công!")
                .result(taskService.assignTask(taskId, userId))
                .build();
    }

    // 5. Cập nhật trạng thái
    @PutMapping("/{taskId}/status/{newStatus}")
    public ApiResponse<Task> updateStatus(@PathVariable String taskId, @PathVariable TaskStatus newStatus) {
        return ApiResponse.<Task>builder()
                .code(1000)
                .message("Cập nhật trạng thái thành công!")
                .result(taskService.updateTaskStatus(taskId, newStatus))
                .build();
    }
}
