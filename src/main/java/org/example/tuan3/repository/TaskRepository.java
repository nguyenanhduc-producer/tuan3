package org.example.tuan3.repository;

import org.example.tuan3.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findByAssignedUserId(String userId);

    List<Task> findByProjectId(String projectId);
}
