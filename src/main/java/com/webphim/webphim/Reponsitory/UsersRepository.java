package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
}