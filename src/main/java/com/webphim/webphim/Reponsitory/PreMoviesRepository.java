package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.PreMovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreMoviesRepository extends JpaRepository<PreMovies, Long> {
    List<PreMovies> findByMovieId(Long id);
}