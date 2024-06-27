package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Poster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosterRepository extends JpaRepository<Poster, Long> {
    void deleteByMovieId(Long movieId);

}