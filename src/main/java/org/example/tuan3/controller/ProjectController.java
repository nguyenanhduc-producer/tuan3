package org.example.tuan3.controller;

import org.example.tuan3.dto.ApiResponse;
import org.example.tuan3.entity.Project;
import org.example.tuan3.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    // 1. Lấy tất cả dự án
    @GetMapping
    public ApiResponse<List<Project>> getAllProjects() {
        return ApiResponse.<List<Project>>builder()
                .code(1000)
                .result(projectService.getAllProjects())
                .build();
    }

    // 2. Tạo dự án mới
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_MANAGER')")
    public ApiResponse<Project> createProject(@RequestBody Project project) {
        return ApiResponse.<Project>builder()
                .code(1000)
                .message("Tạo dự án mới thành công!")
                .result(projectService.saveProject(project))
                .build();
    }
}
