package com.example.senderservice.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@RequiredArgsConstructor
public class CustomerEntity implements Serializable {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String createdBy;
    private String updatedBy;
    private Date updatedAt;
    private Date createdAt;
}
