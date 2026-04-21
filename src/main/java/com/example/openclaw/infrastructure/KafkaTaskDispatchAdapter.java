package com.example.openclaw.infrastructure;

import com.example.openclaw.service.TaskDispatchPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaTaskDispatchAdapter implements TaskDispatchPort {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaTaskDispatchAdapter(KafkaTemplate<String, String> kafkaTemplate,
                                    @Value("${platform.kafka.task-topic:openclaw.task.dispatch}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void dispatch(String taskId) {
        kafkaTemplate.send(topic, taskId, taskId);
    }
}
