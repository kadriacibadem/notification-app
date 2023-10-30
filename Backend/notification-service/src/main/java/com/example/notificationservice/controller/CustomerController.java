package com.example.notificationservice.controller;

import com.example.notificationservice.entity.CustomerEntity;
import com.example.notificationservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity getAllCustomers() {
        if(customerService.getAllCustomers().isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(customerService.getAllCustomers());
        }
    }

    @PostMapping("/create")
    public ResponseEntity createCustomer(@RequestBody CustomerEntity customerEntity) {
        customerService.createCustomer(customerEntity);
        return ResponseEntity.ok().build();
    }
}
