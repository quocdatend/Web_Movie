package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "MOVIES")
public class Movies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String originName;

    @Column(columnDefinition = "TEXT")
    private String contentMovies;

    @Column(nullable = false, length = 15)
    private String typeMovies;

    @Column(nullable = false)
    private int timeMovies;

    @Column(nullable = false, length = 8)
    private String qualityName;

    @Column(nullable = false, length = 4)
    private String year;

    @Column(nullable = false, length = 56)
    private String countryName;

    @Column(nullable = false, length = 255)
    private String slug;

    @Column(nullable = false, length = 75)
    private String directorName;

    @Column(nullable = false)
    private int episodeTotal;

    @Column
    private int episodeCurrent = 0;

    @ManyToOne
    @JoinColumn(name = "preMovieId")
    private PreMovies preMovies;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Poster> poster;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<TrailerMovies> TrailerMovies;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Actor> actors;

    @ManyToMany
    @JoinTable(
            name = "CATEGORY_MOVIES",
            joinColumns = @JoinColumn(name = "MOVIES_ID"),
            inverseJoinColumns = @JoinColumn(name = "CATEGORY_ID")
    )
    private List<Category> categories;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    private List<Episodes> episodes;

}
