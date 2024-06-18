package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.CommentsMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsMovieRepository extends JpaRepository<CommentsMovie, Integer> {
}