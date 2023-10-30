package com.example.notificationservice.tasks;

import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.entity.ReceiverEntity;
import com.example.notificationservice.service.MessageService;
import com.example.notificationservice.service.ReceiverService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FetchMessagesControl {
    private final ReceiverService receiverService;
    private final MessageService messageService;

    @Scheduled(cron = "0 * * * * ?")
    @Transactional
    public void findAndProcessWaitingMessages() {
        try{
            List<MessageEntity> waitingMessages = messageService.waitingMessages();
            for (MessageEntity messageEntity : waitingMessages) {
                processMessage(messageEntity);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void processMessage(MessageEntity messageEntity) {
        List<ReceiverEntity> waitingReceivers = receiverService.getWaitingReceivers(messageEntity.getId());
        int successfulNotifications = 0;

        for (ReceiverEntity receiverEntity : waitingReceivers) {
            String notificationStatus = receiverService.sendNotification(receiverEntity, messageEntity);

            if ("SENT".equals(notificationStatus)) {
                receiverService.updateReceiverStatus(receiverEntity.getId(), "SENT");
                successfulNotifications++;
            } else {
                receiverService.updateReceiverStatus(receiverEntity.getId(), "FAILED");
            }
        }

        updateMessageStatus(messageEntity, successfulNotifications);
    }

    private void updateMessageStatus(MessageEntity messageEntity, int successfulNotifications) {
        if (successfulNotifications == messageEntity.getReceiverEntityList().size()) {
            messageService.updateMessageStatus(messageEntity.getId(), "SENT");
        } else {
            messageService.updateMessageStatus(messageEntity.getId(), "WAITING");
        }
    }

}