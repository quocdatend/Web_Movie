package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.Poster;
import com.webphim.webphim.Model.RatingMovies;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Reponsitory.RatingMoviesRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

@Service
public class RatingService {
    @Autowired
    private RatingMoviesRepository ratingMoviesRepository;

    public double calculateAverageRating(Movies movie) {
        List<RatingMovies> ratings = ratingMoviesRepository.findByMovie(movie);
        if (ratings.isEmpty()) {
            return 0.0;
        }
        double sum = 0;
        for (RatingMovies rating : ratings) {
            sum += rating.getRating();
        }
        double value = sum / ratings.size();
        double scale = Math.pow(10, 1);
        double result = Math.ceil(value * scale) / scale;
        return result;
    }
    public boolean checkuserrating(Users user , Movies movie){
        List<RatingMovies> ratings = ratingMoviesRepository.findByMovie(movie);
        for (RatingMovies rates : ratings){
            if (rates.getUsers().getId() == user.getId())
                return true;
        }
        return false;
    }
    public RatingMovies editRating(Long id, RatingMovies newRatingMovies) {
        RatingMovies existingRating = ratingMoviesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Rating not found"));
        existingRating.setRating(newRatingMovies.getRating());
        existingRating.setUsers(existingRating.getUsers());
        existingRating.setMovie(existingRating.getMovie());
        return ratingMoviesRepository.save(existingRating);
    }
    public void updateRating(Users user , Movies movie, int rating) {
        List<RatingMovies> ratings = ratingMoviesRepository.findByMovie(movie);
        for (RatingMovies rate : ratings){
            if(rate.getUsers().equals(user)){
                rate.setRating(rating);
                editRating(rate.getIdRating(),rate);
            }
        }
    }
}