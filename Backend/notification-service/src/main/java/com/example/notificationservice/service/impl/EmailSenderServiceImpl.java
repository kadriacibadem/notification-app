package com.example.notificationservice.service.impl;

import com.example.notificationservice.service.EmailSenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;
@Service
@RequiredArgsConstructor
public class EmailSenderServiceImpl implements EmailSenderService {
    @Override
    public Boolean sendEmailMock(String from, String to, String subject, String content) {
        if(from == null || to == null || subject == null || content == null)
            return false;
        else{
            String response = fetchDataFromEmailProvider();
            if(response.equals("200"))
                return true;
            else
                return false;
        }
    }

    @Override
    public String fetchDataFromEmailProvider() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try {
            response = restTemplate.getForEntity("http://localhost:8081/email/send", String.class);
        }catch (Exception e) {
            return "500";
        }
        return response.getStatusCode().toString();
    }
}
