package org.example.tuan3.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.example.tuan3.entity.Task;
import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "projects")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "tasks"})
public class Project {
    @Id
    @Column(name = "id", length = 50)
    private String id;

    @Column(name = "projectName", length = 200)
    private String projectName;

    @Column(name = "description")
    private String description;

    // Quan hệ 1-N: Một dự án có nhiều tasks
    // mappedBy = "project" trỏ tới biến 'project' trong class Task
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Task> tasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Project() {

    }
}
