package com.example.notificationservice.service.impl;

import com.example.notificationservice.dtos.MessageDTO;
import com.example.notificationservice.response.MessageResponse;
import com.example.notificationservice.service.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String, MessageResponse> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<String, MessageResponse> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(MessageResponse messageResponse) {
        System.out.println("Sending message to kafka");
        kafkaTemplate.send("notification", messageResponse);
    }
}
