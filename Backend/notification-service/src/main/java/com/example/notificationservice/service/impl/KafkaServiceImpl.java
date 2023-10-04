package com.example.notificationservice.service.impl;

import com.example.notificationservice.dtos.MessageDTO;
import com.example.notificationservice.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String,MessageDTO> kafkaTemplate;

    public KafkaServiceImpl(KafkaTemplate<String, MessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(MessageDTO message) {
        kafkaTemplate.send("notification", message);
    }
}
