package com.example.notificationservice.service.impl;

import com.example.notificationservice.entity.CustomerEntity;
import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.entity.ReceiverEntity;
import com.example.notificationservice.repository.ReceiverRepository;
import com.example.notificationservice.response.MessageResponse;
import com.example.notificationservice.service.ReceiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiverServiceImpl implements ReceiverService {
    private final ReceiverRepository receiverRepository;
    @Override
    public void getMessageFromQueue(MessageEntity messageEntity, MessageResponse messageResponse) {
        List<ReceiverEntity> receiverList = new ArrayList<>();
        for (CustomerEntity customerEntity : messageResponse.getCustomers()) {
            ReceiverEntity receiverEntity = new ReceiverEntity();
            switch (messageEntity.getTemplateEntity().getCategory()){
                case "Mail":
                    receiverEntity.setReceiverAddress(customerEntity.getEmail());
                    break;
                case "SMS":
                    receiverEntity.setReceiverAddress(customerEntity.getPhone());
                    break;
                case "Notification":
                    // Buraya uygulamaya bildirim göndermek için gerekli kodlar yazılacak
                    break;
                default:
                    break;
            }
            receiverEntity.setMessageEntityRecipent(messageEntity);
            receiverList.add(receiverEntity);
        }
        messageEntity.setReceiverEntityList(receiverList);
        for (ReceiverEntity receiverEntity : receiverList) {
            receiverRepository.save(receiverEntity);
        }
    }

    @Override
    public void sendMessageToMail() {

    }

    @Override
    public void sendMessageToSms() {

    }

    @Override
    public void sendMessageToNotification() {

    }
}
