package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.CommentsMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommentsMovieRepository extends JpaRepository<CommentsMovie, Long> {
    List<CommentsMovie> findByMovieId(Long id);
    Optional<CommentsMovie> findByUsersId(Long id);
}