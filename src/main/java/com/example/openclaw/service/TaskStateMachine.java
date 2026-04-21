package com.example.openclaw.service;

import com.example.openclaw.domain.TaskStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class TaskStateMachine {

    private static final Map<TaskStatus, Set<TaskStatus>> ALLOWED_TRANSITIONS = Map.of(
            TaskStatus.INIT, Set.of(TaskStatus.DISPATCHED),
            TaskStatus.DISPATCHED, Set.of(TaskStatus.RUNNING, TaskStatus.FAILED),
            TaskStatus.RUNNING, Set.of(TaskStatus.SUCCESS, TaskStatus.FAILED),
            TaskStatus.SUCCESS, Set.of(),
            TaskStatus.FAILED, Set.of()
    );

    public void ensureTransition(TaskStatus from, TaskStatus to) {
        var allowed = ALLOWED_TRANSITIONS.getOrDefault(from, Set.of());
        if (!allowed.contains(to)) {
            throw new IllegalStateException("Invalid transition: " + from + " -> " + to);
        }
    }
}
