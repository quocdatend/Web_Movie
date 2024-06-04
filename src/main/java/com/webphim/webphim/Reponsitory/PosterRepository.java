package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Poster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosterRepository extends JpaRepository<Poster, Integer> {
}