package com.example.notificationservice.service;

import com.example.notificationservice.entity.TemplateEntity;

import java.util.List;

public interface TemplateService {
    Boolean createTemplate(TemplateEntity templateEntity);
    List<TemplateEntity> getAll();

    TemplateEntity getTemplateById(int id);
}
