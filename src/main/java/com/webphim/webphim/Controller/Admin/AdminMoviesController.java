package com.webphim.webphim.Controller.Admin;

import com.webphim.webphim.Model.*;
import com.webphim.webphim.Service.*;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @Autowired
    private CloudinaryService cloudinaryService;
    @GetMapping("")
    public String Movies(Model model){
        List<Movies> movies = adminMoviesService.getAllMovies();
        model.addAttribute("movies",movies);
        return "Admin/Movie/Movie";
    }
    @GetMapping("/add")
    public String showAddMovieForm(Model model) {
        Movies movie = new Movies();
        movie.setActors(new ArrayList<>());
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("movie", movie);
        return "Admin/Movie/addmovies";
    }

    @SneakyThrows
    @PostMapping("/add")
    public String addMovie(@ModelAttribute("movie") Movies movie,
                           @RequestParam("trailerUrl") String trailerUrl,
                           @RequestParam("posterUrl") MultipartFile posterUrl,
                           @RequestParam("thumbUrl") MultipartFile thumbUrl,
                           Model model) {
        adminMoviesService.saveMovie(movie);

        // Tạo và lưu thông tin poster
        Poster poster = new Poster();
        String thumbUrlString = cloudinaryService.uploadImage(thumbUrl);
        poster.setThumbUrl(thumbUrlString);
        String posterUrlString = cloudinaryService.uploadImage(posterUrl);
        poster.setPosterUrl(posterUrlString);
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
        return "Admin/Movie/EditMovie";
    }
    @PostMapping("/edit/{id}")
    public String editMovie(
            @RequestParam("id") Long id,
            @ModelAttribute("movie") Movies movie,
            @RequestParam(value = "posterUrl", required = false) MultipartFile posterUrl,
            @RequestParam(value = "thumbUrl", required = false) MultipartFile thumbUrl,
            @RequestParam("idp") String idp,
            Model model
            ,BindingResult result) throws IOException {
        if (result.hasErrors()) {
            movie.setId(id);
            return "Admin/EditMovie";
        }
        if(thumbUrl != null || posterUrl != null){
            Poster exitposter =  new Poster();
            exitposter.setMovie(movie);
            if (thumbUrl != null ) {
                String thumbUrlString = cloudinaryService.uploadImage(thumbUrl);
                exitposter.setThumbUrl(thumbUrlString);
            }
            if (posterUrl != null ) {
                String posterUrlString = cloudinaryService.uploadImage(posterUrl);
                exitposter.setPosterUrl(posterUrlString);
            }
            Long idpl = Long.parseLong(idp); // Convert String to Long
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
        return "Admin/Movie/addActor";
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
        return "Admin/Movie/addcat";
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

        return "Admin/Movie/addEpisode";
    }
    @PostMapping("/addEpisode")
    public String addEpisodeToMovie(@ModelAttribute("episode") Episodes episode, @RequestParam("movieId") Long movieId
                                    ,@RequestParam(value = "Eid", required = false) Long idE) {
        if(idE != null){
            episodesService.updateEpisode(idE,episode);
             return "redirect:/AdminMovies/addEpisode/" + movieId;
        }
        Movies movie = adminMoviesService.findid(movieId);
        episode.setMovie(movie);
        episodesService.saveEpisode(episode);
        return "redirect:/AdminMovies/addEpisode/" + movieId;
    }
    @GetMapping("/deleteEpisode/{id}")
    public String deleteEpisode(@PathVariable Long id, Model model) {
        Episodes a = episodesService.getEpisodeById(id);
        episodesService.deteleE(a);
        return "redirect:/AdminMovies/addEpisode/" + a.getMovie().getId();
    }
    @GetMapping ("/editEpisode/{id}")
    public String editE(@PathVariable Long id,Model model) {
        Episodes a = episodesService.getEpisodeById(id);
        Movies movies = a.getMovie();
        List<Episodes> episodes = movies.getEpisodes();
        model.addAttribute("movie", movies);
        model.addAttribute("episode",a);
        model.addAttribute("episodes", episodes);
        return "Admin/Movie/addEpisode";
    }
}

