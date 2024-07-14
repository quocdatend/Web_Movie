/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webphim.webphim.Controller;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.PreMovies;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Acer
 */
@Controller
@RequiredArgsConstructor
public class HomeController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private AdminMoviesService adminMoviesService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PreMovieService preMovieService;
    @GetMapping(value = { "",  "/", "/Home" } )
    public String index(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "3") int size){
        List<Movies> moviesList = new ArrayList<>();
        List<Movies> movies = adminMoviesService.getAllMovies();
        model.addAttribute("movies",movies);
        model.addAttribute("movies0",movies.get(0));
        model.addAttribute("movies1",movies.get(1));
        model.addAttribute("Category",categoryService.getAllCategories());
        Page<Movies> Pmovies = adminMoviesService.GetAll(page,size);
        model.addAttribute("listMovie", Pmovies);
        model.addAttribute("totalPages", Pmovies.getTotalPages());
        return "Home/index";
    }
    @GetMapping("/PreMovie-list")
    public String Mlist(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int size,
                        @AuthenticationPrincipal UserDetails userDetails){
        if (!customUserDetailsService.checkPre(userDetails.getAuthorities().toString())) {
            if (!customUserDetailsService.checkLogin(userDetails.getAuthorities().toString())) {
                return "redirect:/Login_Signup";
            } else {
                return "redirect:/User/Payment";
            }
        }
        List<PreMovies> preMoviesList = preMovieService.GetAllNoAtt();
        List<Movies> moviesList = new ArrayList<>();
        for (PreMovies preMovies: preMoviesList) {
            moviesList.add(preMovies.getMovie());
        }
        model.addAttribute("movies", moviesList);
        model.addAttribute("Category",categoryService.getAllCategories());
        return "Movies/movies";
    }
    @PostMapping("/RedirectPage")
    public String redirectPage(@AuthenticationPrincipal UserDetails userDetails) {
        Users users = usersService.getUserByUsername(userDetails.getUsername());
        if (users == null) {
            return  "redirect:/Admin";
        } else {
            return "redirect:/Home";
        }
    }
}