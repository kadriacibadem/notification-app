package com.example.notificationservice.controller;

import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.service.TemplateService;
import com.example.notificationservice.service.impl.TemplateServiceImpl;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/template")
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping("/create")
    @RateLimiter(name = "default")
    public ResponseEntity create(@RequestBody TemplateEntity templateEntity) {
        if(templateService.createTemplate(templateEntity)) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getall")
    @RateLimiter(name = "default")
    public List<TemplateEntity> getAll() {
        return templateService.getAll();
    }

}