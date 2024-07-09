package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.RatingMovies;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingMoviesRepository extends JpaRepository<RatingMovies, Long> {
    List<RatingMovies> findByMovie(Movies movie);

}