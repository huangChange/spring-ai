package com.example.openclaw.service;

import com.example.openclaw.domain.Task;

import java.util.Optional;

public interface TaskRepository {
    Task save(Task task);

    Optional<Task> findById(String taskId);
}
