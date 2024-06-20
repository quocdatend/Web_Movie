package com.webphim.webphim.Service;

import com.webphim.webphim.Common.Role;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Reponsitory.RoleRepository;
import com.webphim.webphim.Reponsitory.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public void saveUser(Users user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }
    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
    public void setDefaultRole(String username) {
        usersRepository.findByUsername(username).ifPresentOrElse(
                user -> {
                    user.getRoles().add(roleRepository.findRoleById(Role.USER.value));
                    usersRepository.save(user);
                },
                () -> {
                    throw new UsernameNotFoundException("User not found");
                }
        );
    }
    public Optional<Users> findUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }
    @Transactional
    public void setPassUserById(Long id, String password) {
        entityManager.createQuery("UPDATE Users SET password = :password WHERE id = :id")
                .setParameter("password", passwordEncoder.encode(password))
                .setParameter("id", id)
                .executeUpdate();
    }
}
