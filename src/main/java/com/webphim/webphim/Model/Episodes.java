package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "EPISODES")

public class Episodes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long episodeId;

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

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movies movie;
}

