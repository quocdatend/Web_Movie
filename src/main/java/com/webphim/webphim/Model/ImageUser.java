package com.webphim.webphim.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "IMAGE_USER")

public class ImageUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 255)
    private String url;

    @ManyToOne
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "avatarId")
    private AvatarDefault avatarDefault;

    // Getters and setters
}

