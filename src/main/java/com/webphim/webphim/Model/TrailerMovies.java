package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "TRAILER_MOVIES")

public class TrailerMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trailerId;

    @Column(length = 255)
    private String trailerUrl;


    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movies movie;

    // Getters and setters
}