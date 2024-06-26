package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {
    void deleteByMovieId(Long movieId);

}