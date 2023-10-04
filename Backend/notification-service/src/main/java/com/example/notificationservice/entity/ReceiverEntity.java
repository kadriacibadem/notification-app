package com.example.notificationservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "receiver")
@Data
public class ReceiverEntity {
    @Column(name = "receiver_address")
    private String receiverAddress;

    @Column(name = "receiver_position")
    private String position;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Embedded
    private BaseEntity baseEntity;

    @ManyToOne
    @JoinColumn(name = "message_bank_id")
    @JsonIgnore
    private MessageEntity messageEntityRecipent;

}
