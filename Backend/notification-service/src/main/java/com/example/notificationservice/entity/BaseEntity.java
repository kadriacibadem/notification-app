package com.example.notificationservice.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PreUpdate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;

@Embeddable
public class BaseEntity {
    private String createdBy;
    private String updatedBy;
    @UpdateTimestamp
    private Date updatedAt;
    @CreationTimestamp
    private Date createdAt;

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

}
