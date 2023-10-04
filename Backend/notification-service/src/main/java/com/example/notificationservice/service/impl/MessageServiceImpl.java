package com.example.notificationservice.service.impl;

import com.example.notificationservice.entity.CustomerEntity;
import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.repository.TemplateRepository;
import com.example.notificationservice.repository.MessageRepository;
import com.example.notificationservice.response.MessageResponse;
import com.example.notificationservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final TemplateRepository templateRepository;
    private final ReceiverServiceImpl receiverService;


    @Override
    public void createNotification(MessageResponse messageResponse) {
        MessageEntity messageEntity = new MessageEntity();
        Optional<TemplateEntity> templateEntity = Optional.of(templateRepository.findById(messageResponse.getTemplateId()).get());
        templateEntity.ifPresent(messageEntity::setTemplateEntity);
        messageEntity.setChannel(templateEntity.get().getCategory());
        messageEntity.setSender(messageResponse.getSender());
        messageEntity.setActiveFrom(messageResponse.getFromTime());
        messageEntity.setActiveTo(messageResponse.getToTime());
        messageEntity.setPosition("SENDING");
        messageEntity.setAttemptCount(0);
        messageRepository.save(messageEntity);

        if(receiverService.getMessageFromQueue(messageEntity, messageResponse)) {
            messageEntity.setPosition("SENT");
            messageRepository.save(messageEntity);
        }
        else {
            messageEntity.setPosition("FAILED");
            messageRepository.save(messageEntity);
        }
    }
    @Override
    public List<MessageEntity> getAllNotification(){
        return messageRepository.findAll();
    }


    @Override
    public MessageEntity sendNotificationToMail(){
        return messageRepository.findById(4).get();
    }

}
