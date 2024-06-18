package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Admin;
import com.webphim.webphim.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    @Query("SELECT u FROM Admin u WHERE u.name = :username")
    public Admin getAdminByAdminname(@Param("username") String username);
}
