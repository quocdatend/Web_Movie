package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ADMIN")
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, length = 50)
    private String name;

    @Column(name = "EMAIL", nullable = false, length = 60, unique = true) // Consider adding unique constraint
    private String email;

    @Column(name = "PASSWORD", nullable = false, length = 50)
    private String password; // Store securely, not plain text
    // Getters, setters, and other methods
}