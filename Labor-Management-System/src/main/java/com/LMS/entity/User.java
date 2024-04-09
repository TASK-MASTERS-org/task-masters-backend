package com.LMS.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "user")

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fName;
    private String lName;
    @Column(unique = true)
    private String email;
    private String password;
    private String address;
    private String phoneNumber;
    private String role;

    public User() {
        // Set default role to USER in the no-args constructor
        this.role = "USER";
    }
}

