package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.CommentLevel;
import com.webphim.webphim.Model.CommentsMovie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentLevelRepository extends JpaRepository<CommentLevel, Long> {
    Optional<CommentLevel> findByUsersId(Long id);
    List<CommentLevel> findByCommentsMovieId(Long id);
}