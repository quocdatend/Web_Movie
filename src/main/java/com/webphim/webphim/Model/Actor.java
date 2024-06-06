package com.webphim.webphim.Model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ACTOR")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int actorId;

    @Column(nullable = false, length = 75)
    private String actorName;

    @ManyToOne
    @JoinColumn(name = "moviesId", nullable = false)
    private Movies movies;

    // Getters and setters
}
