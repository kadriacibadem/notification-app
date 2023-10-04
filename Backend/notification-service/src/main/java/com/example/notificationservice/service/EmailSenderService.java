package com.example.notificationservice.service;

public interface EmailSenderService {
    void sendEmail(String from,String to, String subject, String content);
}
