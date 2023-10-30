package com.example.notificationservice.service.impl;

import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.repository.TemplateRepository;
import com.example.notificationservice.service.TemplateService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.util.List;

@RequiredArgsConstructor
@Service
@Log4j2
public class TemplateServiceImpl implements TemplateService {
    private final TemplateRepository templateRepository;
    @Transactional
    public Boolean createTemplate(TemplateEntity templateEntity){
        if(templateEntity.equals(null)){
            log.info("Template is null");
            return false;
        }else{
            templateEntity.setPosition(true);
            templateRepository.save(templateEntity);
            log.info("Template saved successfully to id: "+templateEntity.getId());
            return true;
        }
    }

    @Override
    public List<TemplateEntity> getAll(){
        return templateRepository.findAllByPositionTrue();
    }

    @Override
    public TemplateEntity getTemplateById(int id) {
        return templateRepository.findById(id).orElse(null);
    }
}
