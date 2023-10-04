package com.example.notificationservice.repository;

import com.example.notificationservice.entity.ReceiverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends JpaRepository<ReceiverEntity, Integer> {
}
