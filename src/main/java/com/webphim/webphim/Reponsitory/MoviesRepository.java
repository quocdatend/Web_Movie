package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviesRepository extends JpaRepository<Movies, Long> {
}