package com.example.notificationservice.dtos;

import com.example.notificationservice.entity.MessageEntity;
import lombok.Data;

@Data
public class MessageDTO {
    private String sender;
    private String receiver;
    private String subject;
    private String content;

    public MessageDTO(MessageEntity messageEntity) {
        //this.sender = bankEntity.getSender();
        this.sender = "kadriacibadem@gmail.com";
        //this.receiver = bankEntity.getReceiverEntityList().get(0).getReceiverAddress();
        this.receiver = "kadriacibadem@hotmail.com";
        this.subject = "subject";
        this.content = messageEntity.getTemplateEntity().getContext();
    }
}
