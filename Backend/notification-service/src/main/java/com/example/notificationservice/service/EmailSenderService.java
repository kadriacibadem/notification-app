package com.example.notificationservice.service;

public interface EmailSenderService {
    String sendEmail(String from,String to, String subject, String content);
}
