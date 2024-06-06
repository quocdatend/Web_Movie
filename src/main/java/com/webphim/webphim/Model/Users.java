package com.webphim.webphim.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "USERS")

public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usersId;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 50)
    private String password;

    @Column(nullable = false)
    private boolean levelAccount = false;

    @Column(nullable = false, length = 11)
    private String phone;

    // Getters and setters
}
