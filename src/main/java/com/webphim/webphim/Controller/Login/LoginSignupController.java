/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webphim.webphim.Controller.Login;

import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Reponsitory.RoleRepository;
import com.webphim.webphim.Service.CustomUserDetailsService;
import com.webphim.webphim.Service.UsersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 *
 * @author Acer
 */
@Controller
public class LoginSignupController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private RoleRepository roleRepository;
    @GetMapping("/Login_Signup")
    public String index() {
        return "Login_Signup/index";
    }
    @PostMapping("/Signup")
    public String registerUser(Model model, @RequestParam String username, @RequestParam String email, @RequestParam String phone, @RequestParam String password) {//String username,String email,String phone,String password
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);
        usersService.saveUser(user);
        usersService.setDefaultRole(user.getUsername());
        model.addAttribute("successMessage", "Đăng ký thành công!");
        return "Login_Signup/index";
    }
    @GetMapping("/logout")
    public String performLogout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/Home";
    }
}
