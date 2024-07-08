/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webphim.webphim.Controller;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Service.AdminMoviesService;
import com.webphim.webphim.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    private AdminMoviesService adminMoviesService;
    @Autowired
    private CategoryService categoryService;
    @GetMapping(value = { "",  "/", "/Home" } )
    public String index(Model model,
                        @RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "5") int size){
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
    @GetMapping("/Movie-list")
    public String Mlist(Model model){
        List<Movies> movies = adminMoviesService.getAllMovies();
        model.addAttribute("movies",movies);
        model.addAttribute("Category",categoryService.getAllCategories());
        return "Movies/movies";
    }
}
