package com.example.notificationservice.entity;

import jakarta.persistence.Embeddable;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Embeddable
public class BaseEntity {
    private String createdBy;
    private String updatedBy;
    @UpdateTimestamp
    private Date updatedAt;
    @CreationTimestamp
    private Date createdAt;

}
