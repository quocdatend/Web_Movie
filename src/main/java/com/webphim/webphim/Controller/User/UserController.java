package com.webphim.webphim.Controller.User;

import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    private UsersService usersService;
    @GetMapping("/Profile")
    public String showProfileUser(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        Users user = usersService.getUserByUsername(userDetails.getUsername());
        model.addAttribute("user", user);
        return "User/Profile/index";
    }
}
