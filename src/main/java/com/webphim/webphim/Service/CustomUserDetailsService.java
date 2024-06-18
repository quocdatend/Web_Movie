package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Admin;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Reponsitory.AdminRepository;
import com.webphim.webphim.Reponsitory.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository userRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users u = userRepository.getUserByUsername(username);
        Admin a = adminRepository.getAdminByAdminname(username);

        if (u == null && a == null) {
            throw new UsernameNotFoundException("Could not find user");
        }
        if(u != null && a == null) {
            Set<GrantedAuthority> authorities = u.getRoles().stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());
            return new org.springframework.security.core.userdetails.User(
                    username,//"XYZ" +
                    u.getPassword(),
                    authorities
            );
        } else {
            Set<GrantedAuthority> authorities = a.getRoles().stream()
                    .map((role) -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toSet());
            return new org.springframework.security.core.userdetails.User(
                    username,//"XYZ" +
                    a.getPassword(),
                    authorities
            );
        }

    }
}
