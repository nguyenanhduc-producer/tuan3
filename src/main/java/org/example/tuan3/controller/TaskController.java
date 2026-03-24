package org.example.tuan3.controller;

import org.example.tuan3.entity.Task;
import org.example.tuan3.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @GetMapping("/user/{userId}")
    public List<Task> getTasksByUser(@PathVariable String userId) {
        return taskService.getTasksByUser(userId);
    }

    @GetMapping("/project/{projectId}")
    public List<Task> getTasksByProject(@PathVariable String projectId) {
        return taskService.getTasksByProject(projectId);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    // Dòng 5: API Assign Task cho User
    // Link test: PUT http://localhost:8080/api/tasks/T02/assign/U01
    @PutMapping("/{taskId}/assign/{userId}")
    public Task assignTask(@PathVariable String taskId, @PathVariable String userId) {
        return taskService.assignTask(taskId, userId);
    }

    // Dòng 7: API Cập nhật trạng thái Task
    // Link test: PUT http://localhost:8080/api/tasks/T02/status/DONE
    @PutMapping("/{taskId}/status/{newStatus}")
    public Task updateStatus(@PathVariable String taskId, @PathVariable org.example.tuan3.enums.TaskStatus newStatus) {
        return taskService.updateTaskStatus(taskId, newStatus);
    }
}
