package com.webphim.webphim.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "QUALITY_MOVIES")

public class QualityMovies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qualityId;

    @Column(nullable = false, length = 8)
    private String qualityName;

    // Getters and setters
}