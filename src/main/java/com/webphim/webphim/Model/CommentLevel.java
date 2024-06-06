package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Getter
@Setter
@Entity
@Table(name = "COMMENT_LEVEL")
public class CommentLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int commentLevelId;

    @ManyToOne
    @JoinColumn(name = "commendId", nullable = false)
    private CommentsMovie commentsMovie;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String title;

    @ManyToOne
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;

    @Column(nullable = false)
    private Time commentTime;

    @Column(nullable = false)
    private int likes = 0;

    // Getters and setters
}
