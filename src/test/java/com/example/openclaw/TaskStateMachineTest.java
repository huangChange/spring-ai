package com.example.openclaw;

import com.example.openclaw.domain.TaskStatus;
import com.example.openclaw.service.TaskStateMachine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskStateMachineTest {

    private final TaskStateMachine taskStateMachine = new TaskStateMachine();

    @Test
    void shouldAllowHappyPath() {
        assertDoesNotThrow(() -> taskStateMachine.ensureTransition(TaskStatus.INIT, TaskStatus.DISPATCHED));
        assertDoesNotThrow(() -> taskStateMachine.ensureTransition(TaskStatus.DISPATCHED, TaskStatus.RUNNING));
        assertDoesNotThrow(() -> taskStateMachine.ensureTransition(TaskStatus.RUNNING, TaskStatus.SUCCESS));
    }

    @Test
    void shouldBlockInvalidTransition() {
        assertThrows(IllegalStateException.class,
                () -> taskStateMachine.ensureTransition(TaskStatus.INIT, TaskStatus.RUNNING));
    }
}
