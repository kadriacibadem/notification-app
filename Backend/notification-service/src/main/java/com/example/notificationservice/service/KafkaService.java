package com.example.notificationservice.service;

import com.example.notificationservice.dtos.MessageDTO;

public interface KafkaService {
    void sendMessage(MessageDTO message);
}
