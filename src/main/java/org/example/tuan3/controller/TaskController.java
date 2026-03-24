package org.example.tuan3.controller;

import org.example.tuan3.entity.Task;
import org.example.tuan3.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
