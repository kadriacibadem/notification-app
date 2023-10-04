package com.example.notificationservice.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "message_template")
@Data
@NoArgsConstructor
public class TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "template_name")
    private String name;

    @Column(name = "template_context")
    private String context;

    @Column(name = "template_category")
    private String category;

    @Column(name = "template_position")
    private Boolean position;

    @Column(name = "created_ip")
    private String ip;

    @Column(name = "template_attempt_count")
    private int attemptCount;

    @Column(name="exclusion_period_start")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate exclusionStartTime;

    @Column(name="exclusion_period_end")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate exclusionEndTime;

    @Embedded
    private BaseEntity baseEntity;

    @OneToMany(mappedBy = "templateEntity")
    @JsonIgnore
    private List<MessageEntity> messageEntityList = new ArrayList<>();

    public TemplateEntity(String name, String context, String category, String ip) {
        this.name = name;
        this.context = context;
        this.category = category;
        this.ip = ip;
    }
}
