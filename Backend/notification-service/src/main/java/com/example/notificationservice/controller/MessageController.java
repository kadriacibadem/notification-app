package com.example.notificationservice.controller;

import com.example.notificationservice.dtos.MessageDTO;
import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.response.MessageResponse;
import com.example.notificationservice.service.impl.MessageServiceImpl;
import com.example.notificationservice.service.impl.KafkaServiceImpl;
import com.example.notificationservice.service.impl.TemplateServiceImpl;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:3000/")
public class MessageController {
    private final MessageServiceImpl messageService;
    private final KafkaServiceImpl kafkaServiceImpl;
    private final TemplateServiceImpl templateService;

    @PostMapping("/create")
    public String createNotification(@RequestBody MessageResponse messageResponse){
        messageService.createNotification(messageResponse);
        return "success";
    }

    @GetMapping("/getall")
    public List<MessageEntity> getAllNotification(){
        return messageService.getAllNotification();
    }


}
