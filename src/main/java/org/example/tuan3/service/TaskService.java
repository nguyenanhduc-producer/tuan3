package org.example.tuan3.service;

import org.example.tuan3.entity.Task;
import org.example.tuan3.entity.User;
import org.example.tuan3.enums.TaskStatus;
import org.example.tuan3.exception.AppException;
import org.example.tuan3.exception.ErrorCode;
import org.example.tuan3.repository.ProjectRepository;
import org.example.tuan3.repository.TaskRepository;
import org.example.tuan3.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Task> getTasksByUser(String userId) {
        return taskRepository.findByAssignedUserId(userId);
    }

    public List<Task> getTasksByProject(String projectId) {
        return taskRepository.findByProjectId(projectId);
    }

    public Task createTask(Task task) {
        if (task.getProject() == null || task.getProject().getId() == null) {
            // Thay vì IllegalArgumentException, dùng AppException
            throw new AppException(ErrorCode.PROJECT_ID_REQUIRED);
        }

        String projectId = task.getProject().getId();

        projectRepository.findById(projectId)
                .orElseThrow(() -> new AppException(ErrorCode.PROJECT_NOT_FOUND));

        task.setStatus(TaskStatus.TODO);

        return taskRepository.save(task);
    }

    public Task assignTask(String taskId, String userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        task.setAssignedUser(user);
        task.setStatus(TaskStatus.IN_PROGRESS);

        return taskRepository.save(task);
    }

    public Task updateTaskStatus(String taskId, TaskStatus newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new AppException(ErrorCode.TASK_NOT_FOUND));

        if (task.getStatus() == TaskStatus.DONE) {
            throw new AppException(ErrorCode.TASK_ALREADY_DONE);
        }

        task.setStatus(newStatus);
        return taskRepository.save(task);
    }
}
