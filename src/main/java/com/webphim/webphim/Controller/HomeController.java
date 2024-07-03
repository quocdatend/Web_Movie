/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webphim.webphim.Controller;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Service.AdminMoviesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


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
    @GetMapping(value = { "",  "/", "/Home" })
    public String index(Model model){
        List<Movies> moviesList = new ArrayList<>();
        List<Movies> movies = adminMoviesService.getAllMovies();
        model.addAttribute("movies",movies);
        model.addAttribute("movies0",movies.get(0));
        model.addAttribute("movies1",movies.get(1));

        return "Home/index";
    }
    @GetMapping("/Movie-list")
    public String Mlist(){
        return "Movies/movies";
    }

}
