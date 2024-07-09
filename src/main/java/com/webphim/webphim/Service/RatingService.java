package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.RatingMovies;
import com.webphim.webphim.Reponsitory.RatingMoviesRepository;
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
}