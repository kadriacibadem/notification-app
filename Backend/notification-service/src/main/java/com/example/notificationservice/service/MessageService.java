package com.example.notificationservice.service;

import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.response.MessageResponse;

import java.util.List;
import java.util.Optional;

public interface MessageService {
     void createNotificationNow(MessageResponse messageResponse);
     List<MessageEntity> getAllNotification();

     List<MessageEntity> waitingMessages();

     void createNotificationPlan(MessageResponse messageResponse);

     MessageEntity setMessageEntity(MessageResponse messageResponse);
     void sendNotificationToQueue(MessageEntity messageEntity, MessageResponse messageResponse);

     void updateMessageStatus(int id,String position);
}
