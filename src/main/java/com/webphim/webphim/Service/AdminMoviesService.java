package com.webphim.webphim.Service;


import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Reponsitory.MoviesRepository;
import com.webphim.webphim.Reponsitory.PosterRepository;
import com.webphim.webphim.Reponsitory.TrailerMoviesRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AdminMoviesService {
    @Autowired
    private MoviesRepository moviesRepository;
    @Autowired
    private PosterRepository posterRepository;
    @Autowired
    private TrailerMoviesRepository trailerRepository;

    public List<Movies> getAllMovies() {
        return moviesRepository.findAll();
    }
    public void saveMovie(Movies movie) {
        moviesRepository.save(movie);
    }
    public Optional<Movies> findMovie(Long id){
        return moviesRepository.findById(id);
    }
    public void editmovie(@NotNull Movies movies){
            Movies existingMovies = moviesRepository.findById(movies.getId())
                    .orElseThrow(() -> new IllegalStateException("Category with ID " +
                            movies.getId() + " does not exist."));
        existingMovies.setName(movies.getName());
        existingMovies.setContentMovies(movies.getContentMovies());
        existingMovies.setContentMovies(movies.getContentMovies());
        existingMovies.setTypeMovies(movies.getTypeMovies());
        existingMovies.setTimeMovies(movies.getTimeMovies());
        existingMovies.setQualityName(movies.getQualityName());
        existingMovies.setYear(movies.getYear());
        existingMovies.setCountryName(movies.getCountryName());
        existingMovies.setSlug(movies.getSlug());
        existingMovies.setDirectorName(movies.getDirectorName());
        existingMovies.setEpisodeTotal(movies.getEpisodeTotal());
        existingMovies.setEpisodeCurrent(movies.getEpisodeCurrent());
        
        moviesRepository.save(movies);
    }
    @Transactional
    public void deleteProductById(Long id) {
        if (!moviesRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        posterRepository.deleteByMovieId(id);
        trailerRepository.deleteByMovieId(id);
        moviesRepository.deleteById(id);
    }
    public Movies findid(Long id){
        Movies movies = moviesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        return movies;
    }
}
