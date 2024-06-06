package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "COMMENTS_MOVIE")

public class CommentsMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @ManyToOne
    @JoinColumn(name = "movieId", nullable = false)
    private Movies movie;

    @Column(nullable = false)
    private Time commentTime;

    @ManyToOne
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;

    @Column(nullable = false)
    private int likes = 0;

    // Getters and setters
}

