package com.example.notificationservice.repository;

import com.example.notificationservice.entity.MessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, Integer> {

    @Query(value = "SELECT * FROM messages WHERE message_position='WAITING' AND retry_count<3 AND EXTRACT(MINUTE FROM active_from) = EXTRACT(MINUTE FROM NOW());", nativeQuery = true)
    List<MessageEntity> waitingMessages();

    MessageEntity findById(int id);
}
