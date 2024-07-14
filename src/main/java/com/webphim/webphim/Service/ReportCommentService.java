package com.webphim.webphim.Service;

import com.webphim.webphim.Model.ReportComment;
import com.webphim.webphim.Model.Users;
import com.webphim.webphim.Reponsitory.ReportCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
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
    public List<ReportComment> getAll() {return reportCommentRepository.findAll();}

    public Page<ReportComment> GetAll(int pageNo, int pageSize) {
        PageRequest pageRequest =PageRequest.of(pageNo,pageSize);
        return reportCommentRepository.findAll(pageRequest);
    }
    public void deleteById(Long id) {reportCommentRepository.deleteById(id);}
}
