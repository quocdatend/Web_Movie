package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.QualityMovies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QualityMoviesRepository extends JpaRepository<QualityMovies, Integer> {
}