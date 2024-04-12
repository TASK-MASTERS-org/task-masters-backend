package com.LMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long r_Id;
    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user","address"})
    @JoinColumn(name = "order_Id", referencedColumnName = "id",nullable = true)
    private Orders order;

    @ManyToOne
    @JsonIgnoreProperties({"firstname", "lastname", "address", "mobile", "Email", "age",
           "email", "profilePic", "password"})
    @JoinColumn(name = "labor_Id", referencedColumnName = "id",nullable = true)
    private Employee employee;

    private  String review;

    private  String rating;

    private  String serviceType;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",nullable = true)
    @JsonIgnoreProperties({"email", "password", "address", "phoneNumber", "role", "resetToken",
            "password", "enabled", "username", "authorities", "fName",
            "lName", "accountNonExpired", "credentialsNonExpired", "tokenExpirationDate","accountNonLocked"})
    private User user;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "jobPostId", referencedColumnName = "j_Id",nullable = true)
    @JsonIgnoreProperties({"email", "category", "description", "phoneNumber", "date", "resetToken",
            "skills", "location", "budget", "Status"})
    private JobPost jobPost;



}
