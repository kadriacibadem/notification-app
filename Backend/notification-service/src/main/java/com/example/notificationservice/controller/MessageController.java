package com.example.notificationservice.controller;

import com.example.notificationservice.entity.MessageEntity;
import com.example.notificationservice.response.MessageResponse;
import com.example.notificationservice.service.KafkaProducer;
import com.example.notificationservice.service.MessageService;
import com.example.notificationservice.service.TemplateService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {
    private final MessageService messageService;
    private final KafkaProducer kafkaProducer;
    private final TemplateService templateService;

    @PostMapping("/create")
    @RateLimiter(name = "default")
    public String createNotification(@RequestBody MessageResponse messageResponse){
        if(messageResponse.getFromTime()!=null || messageResponse.getToTime()!= null){
            messageService.createNotificationPlan(messageResponse);
        }else{
            messageService.createNotificationNow(messageResponse);
        }
        return "success";
    }

    @GetMapping("/getall")
    @RateLimiter(name = "default")
    public List<MessageEntity> getAllNotification(){
        return messageService.getAllNotification();
    }

}
