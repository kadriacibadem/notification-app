package com.example.notificationservice.controller;

import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.service.impl.TemplateServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/template")
@CrossOrigin(origins = "http://localhost:3000")
public class TemplateController {
    private final TemplateServiceImpl templateServiceImpl;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity create(@RequestBody TemplateEntity templateEntity) {
        if(templateServiceImpl.createTemplate(templateEntity)) {
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/getall")
    public List<TemplateEntity> getAll() {
        return templateServiceImpl.getAll();
    }

}