package com.example.notificationservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "message_batch")
@AllArgsConstructor
@NoArgsConstructor
public class BatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "batch_context")
    private String context;

    @Column(name = "batch_name")
    private String name;

    @Column(name = "batch_category")
    private String category;

    @Column(name = "send_all_customers")
    private Boolean sendAllCustomers;

    @Column(name = "batch_position")
    private Boolean position;

    @Embedded
    private BaseEntity baseEntity;

    @OneToMany(mappedBy = "batchEntity")
    private List<MessageEntity> messageEntityList;
}