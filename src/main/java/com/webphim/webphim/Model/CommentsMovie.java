package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "COMMENTS_MOVIE")

public class CommentsMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "moviesId", nullable = false)
    private Movies movie;
    @ManyToOne
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;
    private String title;
    @Column(nullable = false)
    private LocalDateTime commentTime;
    @Column(nullable = false)
    private int likes = 0;
    @Column(nullable = false)
    private int dislikes = 0;
    // Getters and setters
}

