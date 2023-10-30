package com.example.senderservice.dtos;



import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.time.LocalTime;
import java.util.List;

@Data
@RequiredArgsConstructor
@ToString
public class MessageDTO {
    private int templateId;
    private List<CustomerEntity> customers;
    private String sender;
    private LocalTime fromTime;
    private LocalTime toTime;}

