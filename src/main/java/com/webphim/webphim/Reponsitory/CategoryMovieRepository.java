package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.CategoryMovie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryMovieRepository extends JpaRepository<CategoryMovie, Long> {
}