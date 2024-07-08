package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.Poster;
import com.webphim.webphim.Reponsitory.PosterRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PosterService {


    @Autowired
    private PosterRepository posterRepository;

    public void savePoster(Poster poster){
        posterRepository.save(poster);
    }
    public Poster editPoster(Long id, Poster updatedPoster) {
        return posterRepository.findById(id).map(poster -> {
            poster.setPosterUrl(updatedPoster.getPosterUrl());
            poster.setThumbUrl(updatedPoster.getThumbUrl());
            poster.setMovie(updatedPoster.getMovie());
            return posterRepository.save(poster);
        }).orElseThrow(() -> new RuntimeException("Poster not found with id " + id));
    }
    public Poster findposterid(Long id){
        Poster movies = posterRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        return movies;
    }
    @Transactional
    public void deletePById(Long id) {
        if (!posterRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        posterRepository.deleteById(id);
    }
}