package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Actor;
import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Reponsitory.ActorRepository;
import com.webphim.webphim.Reponsitory.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    @Autowired
    private MoviesRepository moviesRepository;

    public List<Actor> getAllActors() {
        return actorRepository.findAll();
    }

    public Actor find(Long id){
        Actor movies = actorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        return movies;
    }
    public Actor saveActor(Actor actor) {
        return actorRepository.save(actor);
    }

    public List<Movies> getAllMovies() {
        return moviesRepository.findAll();
    }
    public Movies getMovieById(Long id) {
        return moviesRepository.findById(id).orElse(null);
    }

    public void addActorsToMovie(Long movieId, List<Actor> actors) {
        Movies movie = getMovieById(movieId);
        if (movie != null) {
            for (Actor actor : actors) {
                actor.setMovie(movie);
                actorRepository.save(actor);
            }
        }
    }
    public void deletebyid(Long id){
        if (!actorRepository.existsById(id)) {
            throw new IllegalStateException("Product with ID " + id + " does not exist.");
        }
        actorRepository.deleteById(id);
    }
}
