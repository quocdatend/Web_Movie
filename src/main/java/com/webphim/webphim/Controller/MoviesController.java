/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webphim.webphim.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Acer
 */
@Controller
@RequestMapping("/Movies")
public class MoviesController {
    @GetMapping("")
    public String movies() {
        return "Movies/movies";
    }
    @GetMapping("/movie-details")
    public String movie_details() {
        return "Movies/movie-details";
    }
    @GetMapping("/top-movies")
    public String top_movies() {
        return "Movies/top-movies";
    }
}
