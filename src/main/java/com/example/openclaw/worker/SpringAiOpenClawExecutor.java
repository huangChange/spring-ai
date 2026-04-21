package com.example.openclaw.worker;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;

@Component
public class SpringAiOpenClawExecutor implements OpenClawExecutor {

    private final ChatClient chatClient;

    public SpringAiOpenClawExecutor(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @Override
    public String execute(String taskId, String tenantId, String sessionId, String prompt) {
        return chatClient.prompt()
                .system("You are Enterprise OpenClaw executor, return concise and actionable answer.")
                .user(prompt)
                .call()
                .content();
    }
}
