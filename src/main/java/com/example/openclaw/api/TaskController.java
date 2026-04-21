package com.example.openclaw.api;

import com.example.openclaw.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public TaskResponse create(@RequestBody @Valid CreateTaskRequest request) {
        return TaskResponse.from(taskService.create(request.tenantId(), request.sessionId(), request.prompt()));
    }

    @GetMapping("/{taskId}")
    public TaskResponse get(@PathVariable String taskId) {
        return TaskResponse.from(taskService.get(taskId));
    }
}
