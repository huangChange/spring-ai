package com.example.openclaw.service;

import com.example.openclaw.domain.Task;
import com.example.openclaw.domain.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final TaskStateMachine stateMachine;
    private final TaskDispatchPort taskDispatchPort;

    public TaskService(TaskRepository taskRepository, TaskStateMachine stateMachine, TaskDispatchPort taskDispatchPort) {
        this.taskRepository = taskRepository;
        this.stateMachine = stateMachine;
        this.taskDispatchPort = taskDispatchPort;
    }

    public Task create(String tenantId, String sessionId, String prompt) {
        Task task = new Task(UUID.randomUUID().toString(), tenantId, sessionId, prompt);
        taskRepository.save(task);
        transition(task, TaskStatus.DISPATCHED);
        taskDispatchPort.dispatch(task.getTaskId());
        return task;
    }

    public Task get(String taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found: " + taskId));
    }

    public Task markRunning(String taskId) {
        Task task = get(taskId);
        transition(task, TaskStatus.RUNNING);
        return task;
    }

    public Task markSuccess(String taskId, String result) {
        Task task = get(taskId);
        stateMachine.ensureTransition(task.getStatus(), TaskStatus.SUCCESS);
        task.complete(result);
        return taskRepository.save(task);
    }

    public Task markFailed(String taskId, String errorMessage) {
        Task task = get(taskId);
        stateMachine.ensureTransition(task.getStatus(), TaskStatus.FAILED);
        task.fail(errorMessage);
        return taskRepository.save(task);
    }

    private void transition(Task task, TaskStatus nextStatus) {
        stateMachine.ensureTransition(task.getStatus(), nextStatus);
        task.moveTo(nextStatus);
        taskRepository.save(task);
    }
}
