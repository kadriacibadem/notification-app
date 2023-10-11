package com.example.notificationservice.service.impl;

import com.example.notificationservice.entity.CustomerEntity;
import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.entity.ReceiverEntity;
import com.example.notificationservice.repository.ReceiverRepository;
import com.example.notificationservice.response.MessageResponse;
import com.example.notificationservice.service.ReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ReceiverServiceImpl implements ReceiverService {
    private final ReceiverRepository receiverRepository;
    private final EmailSenderServiceImpl emailSenderService;
    private final SmsSenderServiceImpl smsSenderService;
    private Logger log = Logger.getLogger(ReceiverServiceImpl.class.getName());

    @Override
    public Boolean getMessageFromQueue(MessageEntity messageEntity, MessageResponse messageResponse) {
        AtomicBoolean isAllMessagesSent = new AtomicBoolean(true);
        List<ReceiverEntity> receiverList = createReceiverEntities(messageEntity, messageResponse);

        ExecutorService executorService = Executors.newFixedThreadPool(receiverList.size());
        CountDownLatch latch = new CountDownLatch(receiverList.size());

        for (ReceiverEntity receiverEntity : receiverList) {
            executorService.submit(() -> {
                try {
                    String status = sendNotification(receiverEntity, messageEntity);
                    receiverEntity.setPosition(status);
                    if ("FAILED".equals(status))
                        isAllMessagesSent.set(false);
                } finally {
                    log.info("ReceiverEntity saved successfully to id: " + receiverEntity.getId());
                    saveReceiverEntity(receiverEntity);
                    latch.countDown();
                }
            });
        }

        awaitForAllThreadsToComplete(latch);
        executorService.shutdown();

        return isAllMessagesSent.get();
    }

    private List<ReceiverEntity> createReceiverEntities(MessageEntity messageEntity, MessageResponse messageResponse) {
        List<ReceiverEntity> receiverList = new ArrayList<>();
        for (CustomerEntity customerEntity : messageResponse.getCustomers()) {
            ReceiverEntity receiverEntity = new ReceiverEntity();
            String category = messageEntity.getTemplateEntity().getCategory();
            String receiverAddress = getReceiverAddress(category, customerEntity);
            receiverEntity.setReceiverAddress(receiverAddress);
            receiverEntity.setMessageEntityRecipent(messageEntity);
            receiverList.add(receiverEntity);
        }
        messageEntity.setReceiverEntityList(receiverList);
        return receiverList;
    }

    private String getReceiverAddress(String category, CustomerEntity customerEntity) {
        switch (category) {
            case "Mail":
                return customerEntity.getEmail();
            case "SMS":
                return customerEntity.getPhone();
            case "Notification":
                // Bildirim göndermek için gerekli kodları burada ekleyin.
                break;
            default:
                break;
        }
        return null;
    }

    private String sendNotification(ReceiverEntity receiverEntity, MessageEntity messageEntity) {
        if (receiverEntity.getReceiverAddress().contains("@")) {
            return emailSenderService.sendEmail(messageEntity.getSender(), receiverEntity.getReceiverAddress(), "subject", messageEntity.getTemplateEntity().getContext());
        } else if(receiverEntity.getReceiverAddress().contains("0")){
            Boolean status = smsSenderService.sendSms(receiverEntity.getReceiverAddress(), messageEntity.getTemplateEntity().getContext());
            if(status)
                return "Sended";
            else
                return "Failed";
        }else{
            // Bildirim göndermek için gerekli kodları burada eklenecek.
            return "FAILED";
        }
    }

    private void saveReceiverEntity(ReceiverEntity receiverEntity) {
        receiverRepository.save(receiverEntity);
    }

    private void awaitForAllThreadsToComplete(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}