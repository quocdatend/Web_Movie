package com.webphim.webphim.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "imageUser")

public class ImageUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100)
    private String url;

    @OneToOne
    @JoinColumn(name = "usersId")
    private Users users;
    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'https://res-console.cloudinary.com/dap6ivvwp/thumbnails/v1/image/upload/v1719456941/c2xpZGUxX3B0eXEyeQ==/drilldown'")
    private String avatarDefault;

    // Getters and setters
}

