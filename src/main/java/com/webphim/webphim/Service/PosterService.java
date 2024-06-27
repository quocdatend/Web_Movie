package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Poster;
import com.webphim.webphim.Reponsitory.PosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PosterService {


    @Autowired
    private PosterRepository posterRepository;

    public void savePoster(Poster poster){
        posterRepository.save(poster);
    }

}
