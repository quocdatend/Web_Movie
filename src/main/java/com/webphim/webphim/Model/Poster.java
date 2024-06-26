package com.webphim.webphim.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "POSTER")

public class Poster {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPoster;

    @Column(length = 255)
    private String posterUrl;

    @Column(length = 255)
    private String thumbUrl;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movies movie;

    // Getters and setters
}
