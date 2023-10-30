package com.example.notificationservice.service;

public interface EmailSenderService {

    Boolean sendEmailMock(String from, String to, String subject, String content);

    String fetchDataFromEmailProvider();
}
