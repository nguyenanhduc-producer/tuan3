package org.example.tuan3.service;

import org.example.tuan3.entity.Task;
import org.example.tuan3.repository.ProjectRepository;
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

    @Autowired
    private ProjectRepository projectRepository;

    public Task createTask(Task task) {
        if (task.getProject() == null || task.getProject().getId() == null) {
            throw new IllegalArgumentException("Lỗi: Bạn phải nhập ID của Dự án (projectId)!");
        }

        String projectId = task.getProject().getId();

        projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Lỗi: Dự án có ID '" + projectId + "' không tồn tại trong hệ thống!"));

        task.setStatus(org.example.tuan3.enums.TaskStatus.TODO);

        return taskRepository.save(task);
    }

    @Autowired
    private org.example.tuan3.repository.UserRepository userRepository;

    public Task assignTask(String taskId, String userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Lỗi: Không tìm thấy công việc có ID: " + taskId));

        org.example.tuan3.entity.User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Lỗi: Nhân viên có ID '" + userId + "' không tồn tại!"));

        task.setAssignedUser(user);
        task.setStatus(org.example.tuan3.enums.TaskStatus.IN_PROGRESS);

        return taskRepository.save(task);
    }

    public Task updateTaskStatus(String taskId, org.example.tuan3.enums.TaskStatus newStatus) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Lỗi: Không tìm thấy công việc ID: " + taskId));

        if (task.getStatus() == org.example.tuan3.enums.TaskStatus.DONE) {
            throw new IllegalArgumentException("Lỗi: Công việc đã HOÀN THÀNH (DONE), không thể thay đổi trạng thái nữa!");
        }

        task.setStatus(newStatus);
        return taskRepository.save(task);
    }
}
