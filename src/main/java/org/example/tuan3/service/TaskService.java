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

    // Dòng 3 & 4: Logic tạo Task mới kèm Validate
    public Task createTask(Task task) {
        // 1. Validate: Kiểm tra người dùng có gửi kèm thông tin Project không
        if (task.getProject() == null || task.getProject().getId() == null) {
            throw new IllegalArgumentException("Lỗi: Bạn phải nhập ID của Dự án (projectId)!");
        }

        String projectId = task.getProject().getId();

        // 2. Validate: Xuống Database tìm xem Project ID này có thật không
        projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Lỗi: Dự án có ID '" + projectId + "' không tồn tại trong hệ thống!"));

        // 3. Nghiệp vụ: Task mới sinh ra mặc định phải là TODO
        task.setStatus(org.example.tuan3.enums.TaskStatus.TODO);

        // 4. Lưu vào Database
        return taskRepository.save(task);
    }

    // Gọi thêm UserRepository để dò tìm nhân viên (Dòng 6)
    @Autowired
    private org.example.tuan3.repository.UserRepository userRepository;

    // Dòng 5 & 6: Logic Giao việc (Assign Task)
    public Task assignTask(String taskId, String userId) {
        // 1. Tìm xem Task có tồn tại không
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Lỗi: Không tìm thấy công việc có ID: " + taskId));

        // 2. Validate Dòng 6: Kiểm tra User có tồn tại không
        org.example.tuan3.entity.User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Lỗi: Nhân viên có ID '" + userId + "' không tồn tại!"));

        // (Nếu Database của bạn có bảng Trung gian Quản lý thành viên dự án, ta sẽ check thêm logic "User thuộc Project" ở đây. Tạm thời check User tồn tại là OK!)

        // 3. Nghiệp vụ (Dòng 1): Gán User và tự động chuyển trạng thái sang IN_PROGRESS
        task.setAssignedUser(user);
        task.setStatus(org.example.tuan3.enums.TaskStatus.IN_PROGRESS);

        // 4. Lưu lại sự thay đổi
        return taskRepository.save(task);
    }

    // Dòng 7 & 8: Cập nhật trạng thái Task kèm Business Rule
    public Task updateTaskStatus(String taskId, org.example.tuan3.enums.TaskStatus newStatus) {
        // 1. Tìm Task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Lỗi: Không tìm thấy công việc ID: " + taskId));

        // 2. Business Rule (Dòng 8): Nếu đang là DONE thì "khóa hòm", không cho sửa
        if (task.getStatus() == org.example.tuan3.enums.TaskStatus.DONE) {
            throw new IllegalArgumentException("Lỗi: Công việc đã HOÀN THÀNH (DONE), không thể thay đổi trạng thái nữa!");
        }

        // 3. Cập nhật và lưu
        task.setStatus(newStatus);
        return taskRepository.save(task);
    }
}
