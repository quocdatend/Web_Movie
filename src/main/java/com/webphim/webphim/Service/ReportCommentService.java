package com.webphim.webphim.Service;

import com.webphim.webphim.Model.ReportComment;
import com.webphim.webphim.Reponsitory.ReportCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportCommentService {
    @Autowired
    private ReportCommentRepository reportCommentRepository;
    public void save(ReportComment reportComment) {reportCommentRepository.save(reportComment);}
    public Optional<ReportComment> getById(Long id) {return reportCommentRepository.findById(id);}
    public List<ReportComment> getByCommentsMoviesId(Long id) {
        return reportCommentRepository.findByCommentsMovieId(id).stream().toList();
    }
    public List<ReportComment> getByCommentLevelId(Long id) {
        return reportCommentRepository.findByCommentLevelId(id).stream().toList();
    }
}
