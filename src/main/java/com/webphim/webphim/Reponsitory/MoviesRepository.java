package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoviesRepository extends JpaRepository<Movies, Long> {
}