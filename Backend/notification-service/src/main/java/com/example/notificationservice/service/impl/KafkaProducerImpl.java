package com.example.notificationservice.service.impl;

import com.example.notificationservice.dtos.MessageDTO;
import com.example.notificationservice.service.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerImpl implements KafkaProducer {

    private final KafkaTemplate<String,MessageDTO> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<String, MessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MessageDTO message) {
        kafkaTemplate.send("notification", message);
    }
}
