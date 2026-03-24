package org.example.tuan3.service;

import org.example.tuan3.entity.Task;
import org.example.tuan3.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getTasksByUser(String userId) {
        return taskRepository.findByAssignedUserId(userId);
    }

    public List<Task> getTasksByProject(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }
}
