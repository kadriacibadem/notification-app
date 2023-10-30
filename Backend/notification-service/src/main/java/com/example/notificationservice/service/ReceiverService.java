package com.example.notificationservice.service;

import com.example.notificationservice.entity.CustomerEntity;
import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.entity.ReceiverEntity;
import com.example.notificationservice.response.MessageResponse;

import java.util.List;

public interface ReceiverService {
    Boolean getMessageFromQueue(MessageEntity messageEntity, MessageResponse messageResponse);

    List<ReceiverEntity> getWaitingReceivers(Integer id);

    String sendNotification(ReceiverEntity receiverEntity, MessageEntity messageEntity);
    void saveReceiverEntity(ReceiverEntity receiverEntity);
    void updateReceiverStatus(int id, String sent);
    List<ReceiverEntity> createReceiverEntities(MessageEntity messageEntity, MessageResponse messageResponse);
    void createReceiverAndSaveToDBForPlanned(MessageEntity messageEntity, MessageResponse messageResponse);
}
