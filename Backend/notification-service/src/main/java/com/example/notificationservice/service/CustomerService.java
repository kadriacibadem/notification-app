package com.example.notificationservice.service;

import com.example.notificationservice.entity.CustomerEntity;

import java.util.List;

public interface CustomerService {
    List<CustomerEntity> getAllCustomers();

    void createCustomer(CustomerEntity customerEntity);
}
