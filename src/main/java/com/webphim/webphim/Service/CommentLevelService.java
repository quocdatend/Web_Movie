package com.webphim.webphim.Service;

import com.webphim.webphim.Model.CommentLevel;
import com.webphim.webphim.Model.CommentsMovie;
import com.webphim.webphim.Reponsitory.CommentLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CommentLevelService {
    @Autowired
    private CommentLevelRepository commentLevelRepository;
    public void save(CommentLevel commentLevel) {
        commentLevelRepository.save(commentLevel);
    }
    public Optional<CommentLevel> getById(Long id) {return commentLevelRepository.findById(id);}
    public List<CommentLevel> getChildCommentsByCommentsMovieId(long id) {
        List<CommentLevel> commentLevelList = commentLevelRepository.findByCommentsMovieId(id);
        return commentLevelList;
    }
    public ArrayList inCreLike(String postId) {
        Optional<CommentLevel> commentLevel = commentLevelRepository.findById(Long.valueOf(postId));
        commentLevel.stream().toList().get(0).setLikes(commentLevel.stream().toList().get(0).getLikes()+1);
        commentLevelRepository.save(commentLevel.stream().toList().get(0));
        ArrayList lists = new ArrayList<>();
        lists.add(commentLevel.stream().toList().get(0).getLikes());
        lists.add(commentLevel.stream().toList().get(0).getDislikes());
        return lists;
    }
    public ArrayList inCreDisLike(String postId) {
        Optional<CommentLevel> commentLevel = commentLevelRepository.findById(Long.valueOf(postId));
        commentLevel.stream().toList().get(0).setDislikes(commentLevel.stream().toList().get(0).getDislikes()+1);
        commentLevelRepository.save(commentLevel.stream().toList().get(0));
        ArrayList lists = new ArrayList<>();
        lists.add(commentLevel.stream().toList().get(0).getLikes());
        lists.add(commentLevel.stream().toList().get(0).getDislikes());
        return lists;
    }
    public List<CommentLevel> getAll() {return commentLevelRepository.findAll();}
    public void deleteByCommentLevel(CommentLevel commentLevel) {commentLevelRepository.delete(commentLevel);}
}
