package com.example.notificationservice.service.impl;

import com.example.notificationservice.repository.BatchRepository;
import com.example.notificationservice.service.BatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BatchServiceImpl implements BatchService {
    private final BatchRepository batchRepository;
}
