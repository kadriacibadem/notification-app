package com.example.notificationservice.tasks;

import com.example.notificationservice.entity.TemplateEntity;
import com.example.notificationservice.repository.TemplateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TemplateCheck {

    private final TemplateRepository templateRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void checkTemplateEntities(){
        try{
            List<TemplateEntity> templateEntities = templateRepository.getTemplateEntitiesForCheckTime();
            if(!templateEntities.isEmpty()){
                for(TemplateEntity templateEntity : templateEntities){
                    templateEntity.setPosition(false);
                    templateRepository.save(templateEntity);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
