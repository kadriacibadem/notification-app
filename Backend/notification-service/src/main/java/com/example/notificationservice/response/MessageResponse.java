package com.example.notificationservice.response;

import com.example.notificationservice.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageResponse {
    private int templateId;
    private List<CustomerEntity> customers;
    private String sender;
    private LocalTime fromTime;
    private LocalTime toTime;
}
