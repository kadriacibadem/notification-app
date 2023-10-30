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

    @Override
    public void createReceiverAndSaveToDBForPlanned(MessageEntity messageEntity, MessageResponse messageResponse) {
        List<ReceiverEntity> receiverList = createReceiverEntities(messageEntity, messageResponse);
        receiverList.forEach(receiverEntity -> {
            receiverEntity.setPosition("WAITING");
            saveReceiverEntity(receiverEntity);
        });
    }

    @Override
    public List<ReceiverEntity> getWaitingReceivers(Integer id) {
        return receiverRepository.findReceiverEntitiesWaiting(id);
    }

    @Override
    public List<ReceiverEntity> createReceiverEntities(MessageEntity messageEntity, MessageResponse messageResponse) {
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

    @Override
    public String sendNotification(ReceiverEntity receiverEntity, MessageEntity messageEntity) {
        if (messageEntity.getChannel().equals("Mail")) {
            boolean status = emailSenderService.sendEmailMock("from", receiverEntity.getReceiverAddress(), "Subject", messageEntity.getTemplateEntity().getContext());
            if(status)
                return "SENT";
            else
                return "FAILED";
        } else if(messageEntity.getChannel().equals("SMS")){
            boolean status = smsSenderService.sendSms(receiverEntity.getReceiverAddress(), messageEntity.getTemplateEntity().getContext());
            if(status)
                return "SENT";
            else
                return "FAILED";
        }else{
            // Bildirim göndermek için gerekli kodları burada eklenecek.
            return "FAILED";
        }
    }

    @Override
    public void saveReceiverEntity(ReceiverEntity receiverEntity) {
        receiverRepository.save(receiverEntity);
    }

    private void awaitForAllThreadsToComplete(CountDownLatch latch) {
        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void updateReceiverStatus(int id, String sent) {
        ReceiverEntity receiverEntity = receiverRepository.findById(id).orElseThrow();
        receiverEntity.setPosition(sent);
        receiverRepository.save(receiverEntity);
    }

}