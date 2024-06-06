package com.webphim.webphim.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    @Column(name = "CATEGORY_NAME", nullable = false, length = 30)
    private String categoryName;

    @Column(name = "SLUG", nullable = false, length = 30, unique = true)
    private String slug;

    // Getters, setters, and other methods
}
