package com.example.senderservice.service;

import com.example.senderservice.dtos.MessageDTO;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSenderService {
    private final JavaMailSender javaMailSender;

    public void sendEMail(MessageDTO messageDTO) throws MessagingException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(messageDTO.getSender());
        message.setTo(messageDTO.getReceiver());
        message.setSubject(messageDTO.getSubject());
        message.setText(messageDTO.getContent());
        javaMailSender.send(message);
    }
}
