package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    @Query("SELECT u FROM Users u WHERE u.username = :username")
    public Users getUserByUsername(@Param("username") String username);
    //@Query("UPDATE Users SET password = :password WHERE id = :id")
    //public void setUserPasswordById(@Param("password") String password,@Param("id") Long id);
    Optional<Users> findByUsername(String username);
    Users findByEmail(String email);
}