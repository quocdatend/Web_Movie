package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "EPISODES")

public class Episodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int episodeId;

    @ManyToOne
    @JoinColumn(name = "moviesId", nullable = false)
    private Movies movies;

    @Column(nullable = false, length = 10)
    private String name;

    @Column(nullable = false, length = 10)
    private String slug;

    @Column(length = 100)
    private String contentEpisode;

    @Column(length = 255)
    private String linkEmbed;

    @Column(length = 255)
    private String linkM3u8;

    // Getters and setters
}

