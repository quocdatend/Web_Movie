package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "RATING_MOVIES")

public class RatingMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRating;

    @ManyToOne
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;

    @Column(nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "moviesId", nullable = false)
    private Movies movies;

    // Getters and setters
}
