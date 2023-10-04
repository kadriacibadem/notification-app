package com.example.senderservice.service;

import com.example.senderservice.dtos.MessageDTO;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaService {
    private final EmailSenderService emailSenderService;

    @KafkaListener(topics = "notification", groupId = "receive-group")
    public void sendMail(MessageDTO messageDTO) throws MessagingException {
        System.out.println("Received Message in group foo: " + messageDTO);
        emailSenderService.sendEMail(messageDTO);
    }
}
