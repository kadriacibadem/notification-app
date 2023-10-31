package com.example.notificationservice.controller;

import com.example.notificationservice.entity.CustomerEntity;
import com.example.notificationservice.service.CustomerService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/all")
    @RateLimiter(name = "default")
    public ResponseEntity getAllCustomers() {
        if(customerService.getAllCustomers().isEmpty()) {
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(customerService.getAllCustomers());
        }
    }

    @PostMapping("/create")
    @RateLimiter(name = "default")
    public ResponseEntity createCustomer(@RequestBody CustomerEntity customerEntity) {
        customerService.createCustomer(customerEntity);
        return ResponseEntity.ok().build();
    }
}
