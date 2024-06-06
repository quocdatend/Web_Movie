package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "PRE_MOVIES")

public class PreMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int preMovieId;

    @Column(nullable = false)
    private boolean isPre;

    // Getters and setters
}
