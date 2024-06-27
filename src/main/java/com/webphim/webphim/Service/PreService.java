package com.webphim.webphim.Service;

import com.webphim.webphim.Model.PreMovies;
import com.webphim.webphim.Reponsitory.PreMoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreService {
    @Autowired
    private PreMoviesRepository preMoviesRepository;

    public List<PreMovies> getAllPreMovies() {
        return preMoviesRepository.findAll();
    }
}
