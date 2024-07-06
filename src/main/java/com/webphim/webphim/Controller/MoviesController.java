/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webphim.webphim.Controller;

import com.webphim.webphim.Service.AdminMoviesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Acer
 */
@Controller
@RequestMapping("/Movies")
public class MoviesController {
    @Autowired
    private AdminMoviesService adminMoviesService;
    @GetMapping("/movie-details/{id}")
    public String movies(@PathVariable long id, Model model) {
        model.addAttribute("movie", adminMoviesService.findid(id));
        return "Movies/movie-details";
    }
    //    @GetMapping("/movie-details")
//    public String movie_details() {
//        return "Movies/movie-details";
//    }
//    @GetMapping("/top-movies")
//    public String top_movies() {
//        return "Movies/top-movies";
//    }
    @GetMapping("/watching/{id}")
    public  String watching(@PathVariable long id, Model model){
        model.addAttribute("movie", adminMoviesService.findid(id));
        return "Movies/wachting";
    }
}