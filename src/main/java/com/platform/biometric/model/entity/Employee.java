package com.platform.biometric.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String contactNo;
    private String gender;
    //@ManyToOne(targetEntity = Department.class,fetch = FetchType.LAZY)
    //private Department department;
    private String position;
}
