package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.WatchHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WatchHistoryRepository extends JpaRepository<WatchHistory, Integer> {
}