package org.example.tuan3.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "tasks")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Task {
    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "status")
    private String status;

    // Nhiều Task thuộc về 1 Project
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id") // Tên cột khóa ngoại trong DB
    private Project project;

    // Nhiều Task được gán cho 1 User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") // Tên cột khóa ngoại trong DB
    private User assignedUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Task() {

    }

}
