package com.webphim.webphim.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY_MOVIES")
public class CategoryMovie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryMoviesId;

    @ManyToOne
    @JoinColumn(name = "MOVIES_ID", nullable = false)
    private Movies movie;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    // Getters, setters, and other methods
}
