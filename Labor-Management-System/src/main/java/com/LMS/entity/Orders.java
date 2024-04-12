package com.LMS.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnoreProperties({"email", "password", "address", "phoneNumber", "role", "resetToken",
            "tokenExpirationDate", "enabled", "username", "authorities", "fName",
            "lName", "accountNonExpired", "credentialsNonExpired", "accountNonLocked"})
    private User user;


    private String status;
    private String address;

}
