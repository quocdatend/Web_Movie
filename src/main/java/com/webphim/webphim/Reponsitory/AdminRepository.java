package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    @Query("SELECT a FROM Admin a WHERE a.username = :username")
    public Admin getAdminByUsername(@Param("username") String username);

    Optional<Admin> findByUsername(String username);
}
