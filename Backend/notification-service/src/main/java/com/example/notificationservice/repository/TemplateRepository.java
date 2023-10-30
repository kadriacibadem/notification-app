package com.example.notificationservice.repository;

import com.example.notificationservice.entity.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, Integer> {
    List<TemplateEntity> findAllByPositionTrue();

    @Query(value = "SELECT * FROM message_template WHERE exclusion_period_end < current_date",nativeQuery = true)
    List<TemplateEntity> getTemplateEntitiesForCheckTime();

}
