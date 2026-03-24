package org.example.tuan3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.example.tuan3.enums.TaskStatus;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task {
    @Id
    @Column(name = "id", length = 50)
    private String id;

    // --- BẮT ĐẦU PHẦN VALIDATION (Tuần 6) ---

    @NotBlank(message = "Tiêu đề công việc không được để trống")
    @Size(min = 5, max = 200, message = "Tiêu đề phải từ 5 đến 200 ký tự")
    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Size(max = 500, message = "Mô tả không được vượt quá 500 ký tự")
    @Column(name = "description", length = 500)
    private String description;

    @Future(message = "Hạn chót (deadline) phải là một ngày trong tương lai")
    @Column(name = "deadline")
    private LocalDate deadline;

    // --- KẾT THÚC PHẦN VALIDATION ---

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    // Nhiều Task thuộc về 1 Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id") // Tên cột khóa ngoại trong DB
    private Project project;

    // Nhiều Task được gán cho 1 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Tên cột khóa ngoại trong DB
    private User assignedUser;

    // Constructor mặc định
    public Task() {
    }

    // --- GETTERS VÀ SETTERS ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }
}