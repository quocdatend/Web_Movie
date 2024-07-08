package com.webphim.webphim.Service;

import com.webphim.webphim.Model.CommentsMovie;
import com.webphim.webphim.Reponsitory.CommentsMovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CommentsMovieService {
    @Autowired
    private CommentsMovieRepository commentsMovieRepository;
    public Optional<CommentsMovie> getById(Long id) {return commentsMovieRepository.findById(id);}
    public void save(CommentsMovie commentsMovie) {commentsMovieRepository.save(commentsMovie);}
    public List<CommentsMovie> getAllCommentsInMovie(Long id) {
        List<CommentsMovie> commentsMovies = commentsMovieRepository.findByMovieId(id);
        List<CommentsMovie> commentsMoviesSort = new ArrayList<>();
        for (int i = commentsMovies.size()-1; i >=0; i--) {
            commentsMoviesSort.add(commentsMovies.get(i));
        }
        return commentsMoviesSort;
    }
    public List<CommentsMovie> getAllCommentsInMovieByUsersId(Long id) {
        Optional<CommentsMovie> commentsMovies = commentsMovieRepository.findByUsersId(id);
        return commentsMovies.stream().toList();
    }
    public ArrayList inCreLike(String postId) {
        Optional<CommentsMovie> commentsMovie = commentsMovieRepository.findById(Long.valueOf(postId));
        commentsMovie.stream().toList().get(0).setLikes(commentsMovie.stream().toList().get(0).getLikes()+1);
        commentsMovieRepository.save(commentsMovie.stream().toList().get(0));
        ArrayList lists = new ArrayList<>();
        lists.add(commentsMovie.stream().toList().get(0).getLikes());
        lists.add(commentsMovie.stream().toList().get(0).getDislikes());
        return lists;
    }
    public ArrayList inCreDisLike(String postId) {
        Optional<CommentsMovie> commentsMovie = commentsMovieRepository.findById(Long.valueOf(postId));
        commentsMovie.stream().toList().get(0).setDislikes(commentsMovie.stream().toList().get(0).getDislikes()+1);
        commentsMovieRepository.save(commentsMovie.stream().toList().get(0));
        ArrayList lists = new ArrayList<>();
        lists.add(commentsMovie.stream().toList().get(0).getLikes());
        lists.add(commentsMovie.stream().toList().get(0).getDislikes());
        return lists;
    }
}
