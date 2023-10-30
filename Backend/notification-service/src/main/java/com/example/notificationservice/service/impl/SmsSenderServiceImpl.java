package com.example.notificationservice.service.impl;

import com.example.notificationservice.service.SmsSenderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class SmsSenderServiceImpl implements SmsSenderService {
    @Override
    public boolean sendSms(String phoneNumber, String message) {
        if(phoneNumber == null || message == null)
            return false;
        else{
            String response = fetchDataFromSmsProvider();
            if(response.contains("200"))
                return true;
            else
                return false;
        }
    }

    @Override
    public String fetchDataFromSmsProvider() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response;
        try{
           response = restTemplate.getForEntity("http://localhost:8081/sms/send", String.class);
        }catch (Exception e){
            return "500";
        }
        return response.getStatusCode().toString();
    }
}
