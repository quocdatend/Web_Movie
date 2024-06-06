package com.webphim.webphim.Model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "AVATAR_DEFAULT")
public class AvatarDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long avatarId;

    @Column(name = "AVATAR_NAME", nullable = false, length = 20)
    private String avatarName;

    @Column(name = "AVATAR_URL", nullable = false, length = 255)
    private String avatarUrl;

    // Getters, setters, and other methods
}
