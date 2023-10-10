package com.example.notificationservice.service;

import com.example.notificationservice.dtos.MessageDTO;

public interface KafkaProducer {
    void sendMessage(MessageDTO message);
}
