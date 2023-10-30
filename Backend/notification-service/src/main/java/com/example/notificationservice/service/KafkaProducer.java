package com.example.notificationservice.service;

import com.example.notificationservice.dtos.MessageDTO;
import com.example.notificationservice.response.MessageResponse;

public interface KafkaProducer {
    void sendMessage(MessageResponse messageResponse);
}
