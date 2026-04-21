package com.example.openclaw.worker;

import com.example.openclaw.service.TaskService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TaskWorker {

    private final TaskService taskService;
    private final OpenClawExecutor openClawExecutor;

    public TaskWorker(TaskService taskService, OpenClawExecutor openClawExecutor) {
        this.taskService = taskService;
        this.openClawExecutor = openClawExecutor;
    }

    @KafkaListener(topics = "${platform.kafka.task-topic:openclaw.task.dispatch}", groupId = "${platform.kafka.group:openclaw-worker}")
    public void consume(String taskId) {
        var task = taskService.markRunning(taskId);
        try {
            String result = openClawExecutor.execute(task.getTaskId(), task.getTenantId(), task.getSessionId(), task.getPrompt());
            taskService.markSuccess(taskId, result);
        } catch (Exception ex) {
            taskService.markFailed(taskId, ex.getMessage());
        }
    }
}
