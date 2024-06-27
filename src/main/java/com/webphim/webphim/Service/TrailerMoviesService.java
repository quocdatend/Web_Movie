package com.webphim.webphim.Service;

import com.webphim.webphim.Model.TrailerMovies;
import com.webphim.webphim.Reponsitory.TrailerMoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class TrailerMoviesService {
    @Autowired
    private TrailerMoviesRepository trailerMoviesRepository;

    public void saveTrailer(TrailerMovies trailer) {
        trailerMoviesRepository.save(trailer);
    }

}
