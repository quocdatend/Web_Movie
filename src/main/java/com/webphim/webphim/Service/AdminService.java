package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Admin;
import com.webphim.webphim.Reponsitory.AdminRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public Admin getAdminByUsername(String username) {
        return adminRepository.findByUsername(username);
    }
    @Transactional
    public void setAdminByIdUsernameEmail(Long id, String username, String email) {
        entityManager.createQuery("UPDATE Admin SET username = :username, email = :email WHERE id = :id")
                .setParameter("username", username)
                .setParameter("email", email)
                .setParameter("id", id)
                .executeUpdate();
    }
    @Transactional
    public void setAdminByIdUsername(Long id, String username) {
        entityManager.createQuery("UPDATE Admin SET username = :username WHERE id = :id")
                .setParameter("username", username)
                .setParameter("id", id)
                .executeUpdate();
    }
    @Transactional
    public void setAdminByIdEmail(Long id, String email) {
        entityManager.createQuery("UPDATE Admin SET email = :email WHERE id = :id")
                .setParameter("email", email)
                .setParameter("id", id)
                .executeUpdate();
    }
    public Admin getAdminByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

}
