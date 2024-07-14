package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Admin;
import com.webphim.webphim.Model.PaymentHistory;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Reponsitory.AdminRepository;
import com.webphim.webphim.Reponsitory.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private PaymentHistoryService paymentHistoryService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.getUserByUsername(username);
        if(users != null) {
            paymentHistoryService.getIfDurationIsTrue(users.getId(), users);
        }
        Users u = userRepository.getUserByUsername(username);
        Admin a = adminRepository.getAdminByUsername(username);

        if (u == null && a == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        if(u != null && a == null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(u.getUsername())
                    .password(u.getPassword())
                    .authorities(u.getAuthorities())
                    .accountExpired(!u.isAccountNonExpired())
                    .accountLocked(!u.isAccountNonLocked())
                    .credentialsExpired(!u.isCredentialsNonExpired())
                    .disabled(!u.isEnabled())
                    .build();
        } else {
            return org.springframework.security.core.userdetails.User
                    .withUsername(a.getUsername())
                    .password(a.getPassword())
                    .authorities(a.getAuthorities())
                    .accountExpired(!a.isAccountNonExpired())
                    .accountLocked(!a.isAccountNonLocked())
                    .credentialsExpired(!a.isCredentialsNonExpired())
                    .disabled(!a.isEnabled())
                    .build();
        }
    }
    public boolean checkLogin(String role) {
        if(role.equals("[USER]")) {
            return true;
        }
        return false;
    }
    public boolean checkPre(String role) {
        if(role.equals("[PRE]")) {
            return true;
        }
        return false;
    }
}
