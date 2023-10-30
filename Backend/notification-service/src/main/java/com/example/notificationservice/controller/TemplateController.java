package com.example.notificationservice.controller;

import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.service.TemplateService;
import com.example.notificationservice.service.impl.TemplateServiceImpl;
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
    public ResponseEntity create(@RequestBody TemplateEntity templateEntity) {
        if(templateService.createTemplate(templateEntity)) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getall")
    public List<TemplateEntity> getAll() {
        return templateService.getAll();
    }

}