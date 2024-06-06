package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Episodes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodesRepository extends JpaRepository<Episodes, Integer> {
}