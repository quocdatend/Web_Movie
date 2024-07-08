package com.webphim.webphim.Controller.Admin;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.PreMovies;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Service.AdminMoviesService;
import com.webphim.webphim.Service.PreMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/Admin/PreMovies")
public class AdminPreMovieController {
    @Autowired
    private PreMovieService preMovieService;
    @Autowired
    private AdminMoviesService adminMoviesService;
    @GetMapping("")
    public String showPreMovies(Model model,@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size) {
        Page<PreMovies> preMovies = preMovieService.GetAll(page,size);
        model.addAttribute("listPreMovie", preMovies);
        model.addAttribute("totalPages", preMovies.getTotalPages());
        return "Admin/PreMovies/index";
    }
    @GetMapping("/Add")
    public String showListAddMovies(Model model) {
        List<Movies> movies = adminMoviesService.getAllMovies();
        List<Movies> newListMovie = new ArrayList<>();
        List<PreMovies> preMovies = preMovieService.GetAllNoAtt();
        for (Movies movie :movies) {
            boolean isFalse = false;
            for (PreMovies preMovie: preMovies) {
                if(preMovie.getMovie().getId() == movie.getId()) {
                    isFalse = true;
                }
            }
            if (!isFalse) {
                newListMovie.add(movie);
            }
        }
        model.addAttribute("movies",newListMovie);
        return "Admin/PreMovies/AddPreMovie";
    }
    @GetMapping("/Add/{id}")
    public String addPReMovie(@PathVariable Long id) {
        PreMovies preMovies = new PreMovies();
        preMovies.setMovie(adminMoviesService.findMovie(id).stream().toList().get(0));
        preMovieService.save(preMovies);
        return "redirect:/Admin/PreMovies";
    }
    @GetMapping("/Delete/{id}")
    public String DeletePReMovie(@PathVariable Long id) {
        preMovieService.deleteById(id);
        return "redirect:/Admin/PreMovies";
    }
    @GetMapping("/Add/Search")
    public String index(Model model, @RequestParam String name)
    {
        List<Movies> moviesList = adminMoviesService.searchMoviesByName(name);
        List<Movies> newListMovie = new ArrayList<>();
        List<PreMovies> preMovies = preMovieService.GetAllNoAtt();
        for (Movies movie :moviesList) {
            boolean isFalse = false;
            for (PreMovies preMovie: preMovies) {
                if(preMovie.getMovie().getId() == movie.getId()) {
                    isFalse = true;
                }
            }
            if (!isFalse) {
                newListMovie.add(movie);
            }
        }
        model.addAttribute("movies",newListMovie);
        return "Admin/PreMovies/AddPreMovie";
    }
}
