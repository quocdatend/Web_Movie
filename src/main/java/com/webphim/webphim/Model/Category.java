package com.webphim.webphim.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CATEGORY_NAME", nullable = false, length = 30)
    private String categoryName;

    @Column(name = "SLUG", nullable = false, length = 30, unique = true)
    private String slug;

    @ManyToMany(mappedBy = "categories")
    private List<Movies> movies;
    // Getters, setters, and other methods
}
