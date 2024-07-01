package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Long> {
    public Optional<WatchHistory> findById(Long id);
    public List<WatchHistory> findByMovieId(Long id);
    public List<WatchHistory> findByUsersId(Long id);
}