package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "DIRECTOR ")

public class Director {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int directorId;

    @Column(nullable = false, length = 75)
    private String directorName;

    @ManyToOne
    @JoinColumn(name = "moviesId", nullable = false)
    private Movies movies;

    // Getters and setters
}
