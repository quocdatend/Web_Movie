package com.webphim.webphim.Service;

import com.webphim.webphim.Model.Movies;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Model.WatchHistory;
import com.webphim.webphim.Reponsitory.WatchHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WatchHistoryService {
    @Autowired
    private WatchHistoryRepository watchHistoryRepository;
    public void saveWatchHistory(WatchHistory watchHistory) {watchHistoryRepository.save(watchHistory);}
    public Optional<WatchHistory> findById(Long id) {return watchHistoryRepository.findById(id);}
    public boolean checkHistory(Users user, Movies movies) {
//        System.out.println(watchHistoryRepository.findByUsersId(user.getId()).orElse(null));
//        System.out.println(watchHistoryRepository.findByMovieId(movies.getId()).isEmpty());
        if(!watchHistoryRepository.findByUsersId(user.getId()).isEmpty() && watchHistoryRepository.findByMovieId(movies.getId()).isEmpty()) {
            return true;
        }
        if(watchHistoryRepository.findByUsersId(user.getId()).isEmpty() && watchHistoryRepository.findByMovieId(movies.getId()).isEmpty()) {
            return true;
        }
        return false;
    }
    public List<WatchHistory> findByUserId(Long id){return watchHistoryRepository.findByUsersId(id);}
}
