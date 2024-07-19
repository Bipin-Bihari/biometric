package com.platform.biometric.EmployeeServiceImpl;

import com.platform.biometric.model.EmployeePresentTimeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaMessagePublisher {

    @Autowired
    KafkaTemplate<String, Object> kafkaTemplate;

    public void sendMessageToTopic(String key, EmployeePresentTimeResponse message) {
        CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("Attendance-Topic", key, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "] on partition = [" + result.getRecordMetadata().partition() + "]");
            } else {
                System.out.println("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }
}
