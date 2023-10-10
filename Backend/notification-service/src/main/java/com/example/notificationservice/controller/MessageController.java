package com.example.notificationservice.controller;

import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.response.MessageResponse;
import com.example.notificationservice.service.impl.MessageServiceImpl;
import com.example.notificationservice.service.impl.KafkaProducerImpl;
import com.example.notificationservice.service.impl.TemplateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
@CrossOrigin(origins = "http://localhost:3000/")
public class MessageController {
    private final MessageServiceImpl messageService;
    private final KafkaProducerImpl kafkaProducer;
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
