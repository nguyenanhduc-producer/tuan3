package org.example.tuan3.service;

import org.example.tuan3.entity.Project;
import org.example.tuan3.entity.Task;
import org.example.tuan3.entity.User;
import org.example.tuan3.enums.TaskStatus;
import org.example.tuan3.repository.ProjectRepository;
import org.example.tuan3.repository.TaskRepository;
import org.example.tuan3.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    // Kho giả cho Project
    @Mock
    private ProjectRepository projectRepository;

    // Kho giả cho User
    @Mock
    private UserRepository userRepository;

    // Dịch vụ thật cần test
    @InjectMocks
    private TaskService taskService;

    // ---------------------------------------------------------
    // BÀI 1: TEST TẠO TASK (Dòng 2)
    // ---------------------------------------------------------
    @Test
    public void testCreateTask_Success() {
        Task requestTask = new Task();
        requestTask.setId("T99");
        requestTask.setTitle("Học Unit Test với Mockito");
        requestTask.setStatus(TaskStatus.TODO);

        Project project = new Project();
        project.setId("P01");
        requestTask.setProject(project);

        // Giả lập hành vi DB
        Mockito.when(projectRepository.findById("P01")).thenReturn(Optional.of(project));
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(requestTask);

        // Chạy hàm thật
        Task savedTask = taskService.createTask(requestTask);

        // Kiểm tra kết quả
        Assertions.assertNotNull(savedTask, "Task trả về không được phép null");
        Assertions.assertEquals("T99", savedTask.getId(), "ID không khớp");
        Assertions.assertEquals("Học Unit Test với Mockito", savedTask.getTitle(), "Title không khớp");

        Mockito.verify(taskRepository, Mockito.times(1)).save(Mockito.any(Task.class));
    }

    // ---------------------------------------------------------
    // BÀI 2: TEST GIAO VIỆC THÀNH CÔNG (Dòng 3)
    // ---------------------------------------------------------
    @Test
    public void testAssignTask_Success() {
        Task mockTask = new Task();
        mockTask.setId("T01");
        mockTask.setStatus(TaskStatus.TODO);

        User mockUser = new User();
        mockUser.setId("U01");
        mockUser.setUsername("duc_nguyen");

        // Giả lập: Tìm thấy Task và User trong DB
        Mockito.when(taskRepository.findById("T01")).thenReturn(Optional.of(mockTask));
        Mockito.when(userRepository.findById("U01")).thenReturn(Optional.of(mockUser));

        // Giả lập: Lưu thành công
        Mockito.when(taskRepository.save(Mockito.any(Task.class))).thenReturn(mockTask);

        // Gọi hàm thật
        Task assignedTask = taskService.assignTask("T01", "U01");

        // Kiểm tra kết quả xem User đã được nhét vào Task chưa
        Assertions.assertNotNull(assignedTask.getAssignedUser(), "User chưa được gán vào Task");
        Assertions.assertEquals("U01", assignedTask.getAssignedUser().getId(), "Gán nhầm ID User");

        Mockito.verify(taskRepository, Mockito.times(1)).save(mockTask);
    }

    // ---------------------------------------------------------
    // BÀI 3: TEST BẮT LỖI KHI TASK KHÔNG TỒN TẠI (Dòng 7)
    // ---------------------------------------------------------
    @Test
    public void testAssignTask_Fail_TaskNotFound() {
        // Giả lập: Cố tình tìm 1 cái Task ID tào lao và DB trả về rỗng (Optional.empty)
        Mockito.when(taskRepository.findById("T_FAKE")).thenReturn(Optional.empty());

        // Bắt buộc hàm assignTask phải ném ra lỗi RuntimeException
        Assertions.assertThrows(RuntimeException.class, () -> {
            taskService.assignTask("T_FAKE", "U01");
        }, "Hàm không ném ra lỗi khi Task không tồn tại (Code bị sai logic)");
    }
}
