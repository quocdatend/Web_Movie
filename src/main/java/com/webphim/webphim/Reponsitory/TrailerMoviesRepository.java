package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.TrailerMovies;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrailerMoviesRepository extends JpaRepository<TrailerMovies, Integer> {
}