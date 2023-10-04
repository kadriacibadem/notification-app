package com.example.notificationservice.service.impl;

import com.example.notificationservice.service.SmsSenderService;
import org.springframework.stereotype.Service;

@Service
public class SmsSenderServiceImpl implements SmsSenderService {
    @Override
    public boolean sendSms(String phoneNumber, String message) {
        return true;
    }
}
