package com.example.notificationservice.service;

import com.example.notificationservice.entity.CustomerEntity;
import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.response.MessageResponse;

import java.util.List;

public interface ReceiverService {
    Boolean getMessageFromQueue(MessageEntity messageEntity, MessageResponse messageResponse);

}
