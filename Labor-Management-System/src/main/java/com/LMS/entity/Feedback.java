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
    @OneToOne
    @JoinColumn(name = "order_Id", referencedColumnName = "id")
    private Orders order;

    @OneToOne
    @JsonIgnoreProperties({"First_name", "Last_name", "Address", "Mobile", "Email", "age",
            "profilePic", "password"})
    @JoinColumn(name = "labor_Id", referencedColumnName = "id")
    private Employee employee;

    private  String review;

    private  String rating;

    private  String serviceType;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"email", "password", "address", "phoneNumber", "role", "resetToken",
            "password", "enabled", "username", "authorities", "fName",
            "lName", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
    private User user;



}
