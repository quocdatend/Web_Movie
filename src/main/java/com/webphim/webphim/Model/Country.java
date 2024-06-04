package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "COUNTRY")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int countryId;

    @Column(nullable = false, length = 56)
    private String countryName;

    @Column(nullable = false, length = 56)
    private String slug;

    // Getters and setters
}

