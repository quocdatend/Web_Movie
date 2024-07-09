/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.webphim.webphim.Controller;

import com.webphim.webphim.Model.*;
import com.webphim.webphim.Reponsitory.RatingMoviesRepository;
import com.webphim.webphim.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author Acer
 */
@Controller
@RequestMapping("/Movies")
public class MoviesController {
    @Autowired
    private RatingService ratingService;
    @Autowired
    private AdminMoviesService adminMoviesService;
    @Autowired
    private CommentsMovieService commentsMovieService;
    @Autowired
    private UsersService usersService;
    @Autowired
    private CommentLevelService commentLevelService;
    @Autowired
    private ImageUserService imageUserService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private EpisodesService episodesService;
    @Autowired
    private WatchHistoryService watchHistoryService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PreMovieService preMovieService;
    @Autowired
    private RatingMoviesRepository ratingMoviesRepository;
    @GetMapping("/movie-details/{id}")
    public String movies(@PathVariable long id, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (!customUserDetailsService.checkLogin(userDetails.getAuthorities().toString())) {
            if(!customUserDetailsService.checkPre(userDetails.getAuthorities().toString()))
                return "redirect:/Login_Signup";
        }
        List<CommentsMovie> commentsMovieList = commentsMovieService.getAllCommentsInMovie(id);
        List<CommentLevel> commentLevelList = new ArrayList<>();
        List<ImageUser> imageUserCommentList = new ArrayList<>();
        List<ImageUser> imageUserCommentLevelList = new ArrayList<>();
        for (CommentsMovie comment: commentsMovieList) {
            List<CommentLevel> findCommentLevelList = commentLevelService.getChildCommentsByCommentsMovieId(comment.getId());
            for (CommentLevel commentLevel: findCommentLevelList) {
                commentLevelList.add(commentLevel);
            }
        }
        for (CommentsMovie comment: commentsMovieList) {
            imageUserCommentList.add(imageUserService.getImageUserById(comment.getUsers().getId()));
        }
        List<ImageUser> imageUserCommentListNew = new ArrayList<>(new ArrayList<>(new HashSet<>(imageUserCommentList)));
        for (CommentLevel comment: commentLevelList) {
            imageUserCommentLevelList.add(imageUserService.getImageUserById(comment.getUsers().getId()));
        }
        List<ImageUser> imageUserCommentLevelListNew = new ArrayList<>(new ArrayList<>(new HashSet<>(imageUserCommentLevelList)));

        model.addAttribute("movie", adminMoviesService.findid(id));
        model.addAttribute("Category",categoryService.getAllCategories());
        model.addAttribute("listComment", commentsMovieList);
        model.addAttribute("listChildComment", commentLevelList);
        model.addAttribute("listAvatarUserComment", imageUserCommentListNew);
        model.addAttribute("listAvatarUserCommentLevel", imageUserCommentLevelListNew);
        model.addAttribute("nameLink", "movie-details");
        model.addAttribute("rating",ratingService.calculateAverageRating(adminMoviesService.findid(id)));
        return "Movies/movie-details";
    }
    @PostMapping("/movie-details/post-comment")
    public String postComment(@RequestParam String id, @RequestParam String nameLink, @RequestParam String title, @AuthenticationPrincipal UserDetails userDetails) {
        if (!customUserDetailsService.checkPre(userDetails.getAuthorities().toString())) {
            if (!customUserDetailsService.checkLogin(userDetails.getAuthorities().toString())) {
                return "redirect:/Login_Signup";
            }
        }
        LocalDateTime currentDateTime = LocalDateTime.now();
        Users users = usersService.getUserByUsername(userDetails.getUsername());
        CommentsMovie commentsMovie = new CommentsMovie();
        commentsMovie.setMovie(adminMoviesService.findid(Long.valueOf(id)));
        commentsMovie.setUsers(users);
        commentsMovie.setTitle(title);
        commentsMovie.setCommentTime(currentDateTime);
        commentsMovieService.save(commentsMovie);
        return "redirect:/Movies/"+nameLink+"/"+id;
    }
    @PostMapping("/movie-details/post-comment/reply-comment")
    public String replyComment(@RequestParam String MovieId,@RequestParam String id, @RequestParam String nameLink, @RequestParam String title, @AuthenticationPrincipal UserDetails userDetails) {
        if (!customUserDetailsService.checkPre(userDetails.getAuthorities().toString())) {
            if (!customUserDetailsService.checkLogin(userDetails.getAuthorities().toString())) {
                return "redirect:/Login_Signup";
            }
        }
        String newTitle =title.replaceAll("^,+", "").replaceAll(",+$", "");
        LocalDateTime currentDateTime = LocalDateTime.now();
        Users users = usersService.getUserByUsername(userDetails.getUsername());
        CommentLevel commentLevel = new CommentLevel();
        commentLevel.setCommentsMovie(commentsMovieService.getById(Long.valueOf(id)).stream().toList().get(0));
        commentLevel.setUsers(users);
        commentLevel.setTitle(newTitle);
        commentLevel.setCommentTime(currentDateTime);
        commentLevelService.save(commentLevel);
        return "redirect:/Movies/"+nameLink+"/"+MovieId;
    }
    @GetMapping("/movie-details/post-comment/like/{postId}")
    @ResponseBody
    public ArrayList likeComment(@PathVariable String postId) {
        return commentsMovieService.inCreLike(postId);
    }
    @GetMapping("/movie-details/post-comment/disLike/{postId}")
    @ResponseBody
    public ArrayList disLikeComment(@PathVariable String postId) {
        return commentsMovieService.inCreDisLike(postId);
    }
    @GetMapping("/movie-details/post-comment/Child/like/{postId}")
    @ResponseBody
    public ArrayList likeChildComment(@PathVariable String postId) {
        return commentLevelService.inCreLike(postId);
    }
    @GetMapping("/movie-details/post-comment/Child/disLike/{postId}")
    @ResponseBody
    public ArrayList disLikeChildComment(@PathVariable String postId) {
        return commentLevelService.inCreDisLike(postId);
    }
    @GetMapping("/watching/{id}")
    public String watching(@PathVariable long id, Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (!customUserDetailsService.checkLogin(userDetails.getAuthorities().toString())) {
            if(!customUserDetailsService.checkPre(userDetails.getAuthorities().toString()))
                return "redirect:/Login_Signup";
        }
        List<PreMovies> preMovies = preMovieService.getByMovieId(id);
        if(!preMovies.isEmpty()) {
            if(!customUserDetailsService.checkPre(userDetails.getAuthorities().toString())){
                return "redirect:/User/Payment";
            }
        }
        List<CommentsMovie> commentsMovieList = commentsMovieService.getAllCommentsInMovie(id);
        List<CommentLevel> commentLevelList = new ArrayList<>();
        List<ImageUser> imageUserCommentList = new ArrayList<>();
        List<ImageUser> imageUserCommentLevelList = new ArrayList<>();
        for (CommentsMovie comment: commentsMovieList) {
            List<CommentLevel> findCommentLevelList = commentLevelService.getChildCommentsByCommentsMovieId(comment.getId());
            for (CommentLevel commentLevel: findCommentLevelList) {
                commentLevelList.add(commentLevel);
            }
        }
        for (CommentsMovie comment: commentsMovieList) {
            imageUserCommentList.add(imageUserService.getImageUserById(comment.getUsers().getId()));
        }
        List<ImageUser> imageUserCommentListNew = new ArrayList<>(new ArrayList<>(new HashSet<>(imageUserCommentList)));
        for (CommentLevel comment: commentLevelList) {
            imageUserCommentLevelList.add(imageUserService.getImageUserById(comment.getUsers().getId()));
        }
        List<ImageUser> imageUserCommentLevelListNew = new ArrayList<>(new ArrayList<>(new HashSet<>(imageUserCommentLevelList)));
        Movies movies = adminMoviesService.findid(id);
        Users users = usersService.getUserByUsername(userDetails.getUsername());
        LocalTime localTime = LocalTime.now();
        if(watchHistoryService.checkHistory(users, movies)) {
            WatchHistory watchHistory = new WatchHistory();
            watchHistory.setUsers(users);
            watchHistory.setMovie(movies);
            watchHistory.setTime(Time.valueOf(localTime));
            watchHistoryService.saveWatchHistory(watchHistory);
        }
        Episodes episodes = movies.getEpisodes().get(0);
        model.addAttribute("movie", adminMoviesService.findid(id));
        model.addAttribute("episodes", episodes);
        model.addAttribute("Category",categoryService.getAllCategories());
        model.addAttribute("listComment", commentsMovieList);
        model.addAttribute("listChildComment", commentLevelList);
        model.addAttribute("listAvatarUserComment", imageUserCommentListNew);
        model.addAttribute("listAvatarUserCommentLevel", imageUserCommentLevelListNew);
        model.addAttribute("movie", adminMoviesService.findid(id));
        model.addAttribute("nameLink", "watching");
        return "Movies/wachting";
    }
    @RequestMapping("/watching/{id}/{ide}")
    public String watchEpisode(@PathVariable("id") Long id, @PathVariable("ide") Long episodeId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (!customUserDetailsService.checkLogin(userDetails.getAuthorities().toString())) {
            if(!customUserDetailsService.checkPre(userDetails.getAuthorities().toString()))
                return "redirect:/Login_Signup";
        }
        List<PreMovies> preMovies = preMovieService.getByMovieId(id);
        if(!preMovies.isEmpty()) {
            if(!customUserDetailsService.checkPre(userDetails.getAuthorities().toString())){
                return "redirect:/User/Payment";
            }
        }
        Movies movies = adminMoviesService.findid(id);
        Users users = usersService.getUserByUsername(userDetails.getUsername());
        LocalTime localTime = LocalTime.now();
        if(watchHistoryService.checkHistory(users, movies)) {
            WatchHistory watchHistory = new WatchHistory();
            watchHistory.setUsers(users);
            watchHistory.setMovie(movies);
            watchHistory.setTime(Time.valueOf(localTime));
            watchHistoryService.saveWatchHistory(watchHistory);
        }
        Episodes episodes = episodesService.getEpisodeById(episodeId);
        model.addAttribute("movie", movies);
        model.addAttribute("episodes", episodes);
        model.addAttribute("Category",categoryService.getAllCategories());
        return "Movies/wachting";
    }
    @GetMapping("/search")
    public String findbyname(@RequestParam("maneM") String maneM,
                             Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (!customUserDetailsService.checkLogin(userDetails.getAuthorities().toString())) {
            if(!customUserDetailsService.checkPre(userDetails.getAuthorities().toString()))
                return "redirect:/Login_Signup";
        }
        List<Movies> movies =  adminMoviesService.searchMoviesByName(maneM);
        model.addAttribute("movies",movies);
        model.addAttribute("Category",categoryService.getAllCategories());
        return "Movies/movies";
    }
    @GetMapping("/Findbystyle/{cate}")
    public String findbystyle(@PathVariable String cate,
                              Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (!customUserDetailsService.checkLogin(userDetails.getAuthorities().toString())) {
            if(!customUserDetailsService.checkPre(userDetails.getAuthorities().toString()))
                return "redirect:/Login_Signup";
        }
        List<Movies> movies =  adminMoviesService.getAllMovies();
        List<Movies> movies1 = new ArrayList<>();
        for (Movies  movie: movies){
            if(movie.getTypeMovies().equals(cate)){
                movies1.add(movie);
            }
        }
        model.addAttribute("movies",movies1);
        model.addAttribute("Category",categoryService.getAllCategories());
        return "Movies/movies";
    }
    @GetMapping("/Findbycate/{id}")
    public  String findbyCategory(@PathVariable Long id,Model model, @AuthenticationPrincipal UserDetails userDetails){
        if (!customUserDetailsService.checkLogin(userDetails.getAuthorities().toString())) {
            if(!customUserDetailsService.checkPre(userDetails.getAuthorities().toString()))
                return "redirect:/Login_Signup";
        }
        List<Movies> movies = adminMoviesService.getMoviesByCategoryId(id);
        model.addAttribute("movies",movies);
        model.addAttribute("Category",categoryService.getAllCategories());
        return "Movies/movies";
    }
    @PostMapping("/submit_rating")
    public String submitRating(@RequestParam("movieId") Long movieId,
                               @RequestParam("rating") int rating,
                               @AuthenticationPrincipal UserDetails userDetails,
                               Model model) {
        Movies movie = adminMoviesService.findid(movieId);
        Users users = usersService.getUserByUsername(userDetails.getUsername());
        RatingMovies newRating = new RatingMovies();
        newRating.setMovie(movie);
        newRating.setRating(rating);
        newRating.setUsers(users);
        ratingMoviesRepository.save(newRating);
        return "redirect:/Movies/movie-details/" + movieId;
    }
}