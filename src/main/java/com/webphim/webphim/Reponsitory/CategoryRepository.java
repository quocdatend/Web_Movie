package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}