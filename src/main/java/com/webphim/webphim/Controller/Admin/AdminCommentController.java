package com.webphim.webphim.Controller.Admin;

import com.webphim.webphim.Model.CommentLevel;
import com.webphim.webphim.Model.CommentsMovie;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Service.CommentLevelService;
import com.webphim.webphim.Service.CommentsMovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/Admin/Comment")
public class AdminCommentController {
    @Autowired
    private CommentLevelService commentLevelService;
    @Autowired
    private CommentsMovieService commentsMovieService;
    @GetMapping("")
    public String showComment(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size) {
        Page<CommentsMovie> commentsMovieList = commentsMovieService.GetAllForPage(page,size);
        model.addAttribute("listComment", commentsMovieList);
        model.addAttribute("totalPages", commentsMovieList.getTotalPages());
        return "Admin/Comments/index";
    }
    @GetMapping("/{id}")
    public String showComment(Model model, @PathVariable Long id) {
        List<CommentLevel> commentLevelList = commentLevelService.getChildCommentsByCommentsMovieId(id);
        model.addAttribute("listCommentLevel", commentLevelList);
        return "Admin/Comments/CommentLevel";
    }
}
