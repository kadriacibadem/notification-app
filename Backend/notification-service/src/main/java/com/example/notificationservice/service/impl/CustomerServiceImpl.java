package com.example.notificationservice.service.impl;

import com.example.notificationservice.entity.CustomerEntity;
import com.example.notificationservice.repository.CustomerRepository;
import com.example.notificationservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private Logger log = Logger.getLogger(CustomerServiceImpl.class.getName());
    @Override
    public List<CustomerEntity> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void createCustomer(CustomerEntity customerEntity) {
        customerRepository.save(customerEntity);
        log.info("Customer created successfully");
    }
}
