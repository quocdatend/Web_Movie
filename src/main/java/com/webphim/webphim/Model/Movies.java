package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "MOVIES")

public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int movieId;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String originName;

    @Column(columnDefinition = "TEXT")
    private String contentMovies;

    @Column(nullable = false, length = 15)
    private String typeMovies;

    @Column(nullable = false)
    private int timeMovies;

    @ManyToOne
    @JoinColumn(name = "qualityId", nullable = false)
    private QualityMovies qualityMovies;

    @Column(nullable = false, length = 4)
    private String year;

    @ManyToOne
    @JoinColumn(name = "countryId", nullable = false)
    private Country country;

    @Column(nullable = false, length = 255)
    private String slug;

    @Column(nullable = false)
    private int episodeTotal;

    @Column
    private int episodeCurrent = 0;

    @ManyToOne
    @JoinColumn(name = "preMovieId")
    private PreMovies preMovies;

    // Getters and setters
}
