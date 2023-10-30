package com.example.notificationservice.service.impl;

import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.repository.TemplateRepository;
import com.example.notificationservice.repository.MessageRepository;
import com.example.notificationservice.response.MessageResponse;
import com.example.notificationservice.service.MessageService;
import com.example.notificationservice.service.ReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final TemplateRepository templateRepository;
    private final ReceiverService receiverService;
    private Logger log = Logger.getLogger(MessageServiceImpl.class.getName());


    @Override
    public void createNotificationNow(MessageResponse messageResponse) {
        MessageEntity messageEntity = setMessageEntity(messageResponse);
        messageEntity.setPosition("SENDING");
        messageRepository.save(messageEntity);
        sendNotificationToQueue(messageEntity, messageResponse);
    }

    @Override
    public void sendNotificationToQueue(MessageEntity messageEntity, MessageResponse messageResponse) {
        if(receiverService.getMessageFromQueue(messageEntity, messageResponse)) {
            messageEntity.setPosition("SENT");
            log.info("Message sent successfully to id: "+messageEntity.getId());
            messageRepository.save(messageEntity);
        }
        else {
            messageEntity.setPosition("FAILED");
            log.info("Message failed to send id: "+messageEntity.getId());
            messageRepository.save(messageEntity);
        }
    }
    @Override
    public void updateMessageStatus(int id,String position) {
        MessageEntity messageEntity = messageRepository.findById(id);

        if (position.equals("WAITING")) {
            LocalTime activeTo = messageEntity.getActiveTo();
            if (messageEntity.getActiveFrom().isAfter(activeTo)) {
                messageEntity.setPosition("TIMEOUT");
            }else{
                messageEntity.setActiveFrom(messageEntity.getActiveFrom().plusMinutes(2));
                if (messageEntity.getActiveFrom().isAfter(activeTo)) {
                    messageEntity.setRetryCount(messageEntity.getRetryCount() + 1);
                    messageEntity.setPosition("TIMEOUT");
                }else{
                    messageEntity.setRetryCount(messageEntity.getRetryCount() + 1);
                    if(messageEntity.getRetryCount() == 3){
                        messageEntity.setPosition("FAILED");
                    }
                }
            }
        }else{
            messageEntity.setPosition(position);
        }
        messageRepository.save(messageEntity);
    }

    @Override
    public List<MessageEntity> getAllNotification(){
        return messageRepository.findAll();
    }

    @Override
    public List<MessageEntity> waitingMessages() {
        return messageRepository.waitingMessages();
    }

    @Override
    public void createNotificationPlan(MessageResponse messageResponse) {
        MessageEntity messageEntity = setMessageEntity(messageResponse);
        messageEntity.setPosition("WAITING");
        messageRepository.save(messageEntity);
        receiverService.createReceiverAndSaveToDBForPlanned(messageEntity, messageResponse);
    }

    @Override
    public MessageEntity setMessageEntity(MessageResponse messageResponse) {
        MessageEntity messageEntity = new MessageEntity();
        Optional<TemplateEntity> templateEntity = Optional.of(templateRepository.findById(messageResponse.getTemplateId()).get());
        templateEntity.ifPresent(messageEntity::setTemplateEntity);
        messageEntity.setChannel(templateEntity.get().getCategory());
        messageEntity.setSender(messageResponse.getSender());
        messageEntity.setActiveFrom(messageResponse.getFromTime());
        messageEntity.setActiveTo(messageResponse.getToTime());
        return messageEntity;
    }


}
