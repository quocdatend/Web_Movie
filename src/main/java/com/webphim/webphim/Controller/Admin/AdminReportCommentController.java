package com.webphim.webphim.Controller.Admin;

import com.webphim.webphim.Model.CommentLevel;
import com.webphim.webphim.Model.ReportComment;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Service.CommentLevelService;
import com.webphim.webphim.Service.CommentsMovieService;
import com.webphim.webphim.Service.ReportCommentService;
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
@RequestMapping("/Admin/Report")
public class AdminReportCommentController {
    @Autowired
    private ReportCommentService reportCommentService;
    @Autowired
    private CommentsMovieService commentsMovieService;
    @Autowired
    private CommentLevelService commentLevelService;
    @GetMapping("")
    public String showAllReport(Model model,@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size) {
        Page<ReportComment> reportCommentList = reportCommentService.GetAll(page,size);
        model.addAttribute("totalPages", reportCommentList.getTotalPages());
        model.addAttribute("reportCommentList", reportCommentList);
        return "Admin/Report/index";
    }
    @GetMapping("/Delete/{id}")
    public String delete(@PathVariable String id, Model model) {
        reportCommentService.deleteById(Long.valueOf(id));
        return "redirect:/Admin/Report";
    }
    @GetMapping("/DeleteComment/{id}")
    public String deleteComment(@PathVariable String id, Model model) {
        List<ReportComment> reportComment = reportCommentService.getById(Long.valueOf(id)).stream().toList();
        reportCommentService.deleteById(Long.valueOf(id));
        if(reportComment.get(0).getCommentsMovie() != null) {
            List<CommentLevel> commentLevelList = commentLevelService.getChildCommentsByCommentsMovieId(reportComment.get(0).getCommentsMovie().getId());
            for (CommentLevel commentLevel: commentLevelList) {
                commentLevelService.deleteByCommentLevel(commentLevel);
            }
            commentsMovieService.deleteByCommentsMovie(reportComment.get(0).getCommentsMovie());
        } else {
            commentLevelService.deleteByCommentLevel(reportComment.get(0).getCommentLevel());
        }
        return "redirect:/Admin/Report";
    }
}
