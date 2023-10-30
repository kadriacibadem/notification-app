package com.example.notificationservice.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "messages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "sender")
    private String sender;

    @Column(name="message_position")
    private String position;

    @Column(name="active_from")
    private LocalTime activeFrom;

    @Column(name="active_to")
    private LocalTime activeTo;

    @Column(name="message_channel")
    private String channel;

    @Column(name="retry_count")
    private int retryCount=0;

    @Embedded
    private BaseEntity baseEntity;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private TemplateEntity templateEntity;

    @ManyToOne
    @JoinColumn(name = "batch_id")
    @JsonIgnore
    private BatchEntity batchEntity;

    @OneToMany(mappedBy = "messageEntityRecipent")
    private List<ReceiverEntity> receiverEntityList = new ArrayList<>();
}
