package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    private String name;
    @ManyToMany(mappedBy = "roles")
    private Set<Users> user = new HashSet<>();
}
