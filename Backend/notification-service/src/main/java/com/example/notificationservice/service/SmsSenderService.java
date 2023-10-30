package com.example.notificationservice.service;

public interface SmsSenderService {
    boolean sendSms(String phoneNumber, String message);
    String fetchDataFromSmsProvider();
}
