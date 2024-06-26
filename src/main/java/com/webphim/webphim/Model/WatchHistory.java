package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "WATCH_HISTORY")

public class WatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int watchId;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movies movie;

    @Column(precision = 2, scale = 2)
    private BigDecimal timestamp;

    @ManyToOne
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;

    // Getters and setters
}