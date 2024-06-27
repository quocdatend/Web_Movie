package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.ImageUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageUserRepository extends JpaRepository<ImageUser, Long> {
}