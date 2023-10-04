package com.example.notificationservice.service.impl;

import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.repository.TemplateRepository;
import com.example.notificationservice.service.TemplateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    @Transactional
    public Boolean createTemplate(TemplateEntity templateEntity){
        if(templateEntity.equals(null)){
            return false;
        }else{
            templateEntity.setPosition(true);
            System.out.println(templateEntity.getExclusionStartTime());
            templateRepository.save(templateEntity);
            return true;
        }
    }

    public List<TemplateEntity> getAll(){
        return templateRepository.findAll();
    }

    @Override
    public TemplateEntity getTemplateById(int id) {
        return templateRepository.findById(id).orElse(null);
    }
}
