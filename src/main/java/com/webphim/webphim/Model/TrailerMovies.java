package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "TRAILER_MOVIES")

public class TrailerMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int trailerId;

    @Column(length = 255)
    private String trailerUrl;

    @Column
    private Time time;

    @ManyToOne
    @JoinColumn(name = "movieId", nullable = false)
    private Movies movie;

    // Getters and setters
}