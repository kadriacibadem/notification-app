package com.example.notificationservice.service;

import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.response.MessageResponse;

import java.util.List;
import java.util.Optional;

public interface MessageService {
     void createNotification(MessageResponse messageResponse);
     List<MessageEntity> getAllNotification();
    MessageEntity sendNotificationToMail();
}
