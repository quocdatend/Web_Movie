package com.webphim.webphim.Controller.Admin;

import com.webphim.webphim.Model.*;
import com.webphim.webphim.Service.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/AdminMovies")
@Controller
public class AdminMoviesController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private AdminMoviesService adminMoviesService;
    @Autowired
    private PosterService posterService;
    @Autowired
    private TrailerMoviesService trailerMoviesService;
    @Autowired
    private ActorService actorService;
    @Autowired
    private EpisodesService episodesService;
    @GetMapping("")
    public String Movies(Model model){
        List<Movies> movies = adminMoviesService.getAllMovies();
        model.addAttribute("movies",movies);
        return "Admin/Movie";
    }
    @GetMapping("/add")
    public String showAddMovieForm(Model model) {
        Movies movie = new Movies();
        movie.setActors(new ArrayList<>());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("movie", movie);
        return "Admin/addmovies";
    }

    @SneakyThrows
    @PostMapping("/add")
    public String addMovie(@ModelAttribute("movie") Movies movie,
                           @RequestParam("trailerUrl") String trailerUrl,
                           @RequestParam("posterUrl") String posterUrl,
                           @RequestParam("thumbUrl") String thumbUrl,
                           Model model) {
        adminMoviesService.saveMovie(movie);

        // Tạo và lưu thông tin poster
        Poster poster = new Poster();
        poster.setThumbUrl(thumbUrl);
        poster.setPosterUrl(posterUrl);
        poster.setMovie(movie);
        posterService.savePoster(poster);

        // Tạo và lưu thông tin trailer
        TrailerMovies trailerMovies = new TrailerMovies();
        trailerMovies.setMovie(movie);
        trailerMovies.setTrailerUrl(trailerUrl);
        trailerMoviesService.saveTrailer(trailerMovies);

        model.addAttribute("movies", adminMoviesService.getAllMovies());

        return "redirect:/AdminMovies/edit/" + movie.getId();
    }
    @GetMapping("/edit/{id}")
    public String showEdit(@PathVariable Long id ,Model model){
        Movies movies = adminMoviesService.findMovie(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("movie", adminMoviesService.findMovie(id));
        return "Admin/EditMovie";
    }
    @PostMapping("/edit/{id}")
    public String editMovie(
            @RequestParam("id") Long id,
            @ModelAttribute("movie") Movies movie, Model model
        ,BindingResult result) {
        if (result.hasErrors()) {
            movie.setId(id);
            return "Admin/EditMovie";
        }
        adminMoviesService.editmovie(movie);
        model.addAttribute("movie", adminMoviesService.getAllMovies());
        return "redirect:/AdminMovies";
    }
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        Movies movies = adminMoviesService.findMovie(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        adminMoviesService.deleteProductById(id);
        return "redirect:/AdminMovies";
    }
    @GetMapping("/addActor/{movieId}")
    public String showAddActorForm(@PathVariable Long movieId, Model model) {
        Movies movie = adminMoviesService.findid(movieId);
        model.addAttribute("movie", movie);
        model.addAttribute("actor", new Actor());
        List<Actor> actors = movie.getActors();
        model.addAttribute("actors", actors);
        return "Admin/addActor";
    }
    @PostMapping("/addActor")
    public String addActor(@RequestParam("movieId") Long movieId, @ModelAttribute Actor actor) {
        Movies movie = adminMoviesService.findid(movieId);
        actor.setMovie(movie);
        movie.getActors().add(actor);
        adminMoviesService.saveMovie(movie);
        return "redirect:/AdminMovies/addActor/" + movieId;
    }
    @GetMapping("/deleteactor/{actorId}")
    public String deleteactor(@PathVariable Long actorId, Model model ){
        Actor a = actorService.find(actorId);
        actorService.deletebyid(actorId);
        return "redirect:/AdminMovies/addActor/" + a.getMovie().getId();
    }
    @GetMapping("/addCategory/{movieId}")
    public String showAddCategoryForm(@PathVariable Long movieId, Model model) {
        Movies movie = adminMoviesService.findid(movieId);
        model.addAttribute("movie", movie);
        model.addAttribute("category", new Category());
        model.addAttribute("category2", categoryService.getAllCategories());
        List<Category> category2 = movie.getCategories();
        model.addAttribute("category1", category2);
        return "Admin/addcat";
    }

    @PostMapping("/addCategory/{movieId}")
    public String addCategoryToMovie(@PathVariable Long movieId, @RequestParam("category") Long categoryId) {
        categoryService.addCategoryToMovie(movieId, categoryId);
        return "redirect:/AdminMovies/addCategory/" + movieId;
    }

    @PostMapping("/deleteCategory/{movieId}")
    public String deleteCategoryFromMovie(@PathVariable Long movieId, @RequestParam("categoryId") Long categoryId) {
        categoryService.removeCategoryFromMovie(movieId, categoryId);
        return "redirect:/AdminMovies/addCategory/" + movieId;
    }


    //EpisodesService

    // Phương thức để hiển thị biểu mẫu thêm tập phim mới cho một bộ phim
    @GetMapping("/addEpisode/{movieId}")
    public String showAddEpisodeForm(@PathVariable Long movieId, Model model) {
        Movies movie = adminMoviesService.findid(movieId);
        model.addAttribute("movie", movie);

        List<Episodes> episodes = movie.getEpisodes();
        model.addAttribute("episodes", episodes);

        model.addAttribute("episode", new Episodes());

        return "Admin/addEpisode";
    }
    @PostMapping("/addEpisode")
    public String addEpisodeToMovie(@ModelAttribute("episode") Episodes episode, @RequestParam("movieId") Long movieId) {
        Movies movie = adminMoviesService.findid(movieId);
        episode.setMovie(movie);
        episodesService.saveEpisode(episode);
        return "redirect:/AdminMovies/edit/" + movieId;
    }

}

