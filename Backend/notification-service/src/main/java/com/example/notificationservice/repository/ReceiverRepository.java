package com.example.notificationservice.repository;

import com.example.notificationservice.entity.ReceiverEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceiverRepository extends JpaRepository<ReceiverEntity, Integer> {
    String queryForWaitingReceiver = "SELECT * FROM receiver WHERE receiver_position='WAITING' AND message_bank_id=?1";
    @Query(value = queryForWaitingReceiver, nativeQuery = true)
    List<ReceiverEntity> findReceiverEntitiesWaiting(Integer id);
}
