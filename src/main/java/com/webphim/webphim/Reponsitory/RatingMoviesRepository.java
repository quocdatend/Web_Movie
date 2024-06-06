package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.RatingMovies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingMoviesRepository extends JpaRepository<RatingMovies, Integer> {
}