package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.TrailerMovies;
import com.webphim.webphim.Reponsitory.TrailerMoviesRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class TrailerMoviesService {
    @Autowired
    private TrailerMoviesRepository trailerMoviesRepository;

    public void saveTrailer(TrailerMovies trailer) {
        trailerMoviesRepository.save(trailer);
    }

}
