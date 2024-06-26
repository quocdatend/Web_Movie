package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.TrailerMovies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TrailerMoviesRepository extends JpaRepository<TrailerMovies, Long> {

    void deleteByMovieId(Long movieId);

}