package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Episodes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodesRepository extends JpaRepository<Episodes, Long> {
}