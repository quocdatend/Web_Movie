package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Time;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "WATCH_HISTORY")

public class WatchHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movies movie;
    @ManyToOne
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;

    private Time time;
    // Getters and setters
}