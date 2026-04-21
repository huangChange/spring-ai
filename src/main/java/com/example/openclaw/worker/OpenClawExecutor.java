package com.example.openclaw.worker;

public interface OpenClawExecutor {
    String execute(String taskId, String tenantId, String sessionId, String prompt);
}
