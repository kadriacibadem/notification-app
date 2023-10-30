package com.example.senderservice.service;

import com.example.senderservice.dtos.MessageDTO;

public interface KafkaConsumerService {
    void getMessageFromKafka(MessageDTO messageDTO);
}
