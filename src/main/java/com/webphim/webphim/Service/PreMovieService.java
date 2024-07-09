package com.webphim.webphim.Service;

import com.webphim.webphim.Model.PreMovies;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Reponsitory.PreMoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreMovieService {
    @Autowired
    private PreMoviesRepository preMoviesRepository;
    public void save(PreMovies preMovies) {preMoviesRepository.save(preMovies);}
    public void deleteById(Long id) {preMoviesRepository.deleteById(id);}
    public Page<PreMovies> GetAll(int pageNo, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNo,pageSize);
        return preMoviesRepository.findAll(pageRequest);
    }
    public List<PreMovies> GetAllNoAtt() { return preMoviesRepository.findAll();}
    public List<PreMovies> getById(long id) {
        return preMoviesRepository.findById(id).stream().toList();
    }

    public List<PreMovies> getByMovieId(Long id) {
        return preMoviesRepository.findByMovieId(id);
    }
}
