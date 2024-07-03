package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRE_MOVIES")

public class PreMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preMovieId;
    @OneToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movies movie;


    // Getters and setters
}
