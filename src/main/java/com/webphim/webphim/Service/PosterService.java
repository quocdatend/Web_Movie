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

}
