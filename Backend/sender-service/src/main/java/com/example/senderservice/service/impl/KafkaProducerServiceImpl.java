package com.example.senderservice.service.impl;

import com.example.senderservice.dtos.MessageDTO;
import com.example.senderservice.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Configuration
public class KafkaProducerServiceImpl implements KafkaProducerService {
    private final KafkaTemplate<String, MessageDTO> kafkaTemplate;


    void sendMessageToKafka(MessageDTO messageDTO) {
        kafkaTemplate.send("notification", messageDTO);
    }
}
