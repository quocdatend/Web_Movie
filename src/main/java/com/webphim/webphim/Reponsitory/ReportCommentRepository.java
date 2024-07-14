package com.webphim.webphim.Reponsitory;

import com.webphim.webphim.Model.Actor;
import com.webphim.webphim.Model.ReportComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {
    Optional<ReportComment> findByCommentLevelId(Long valueOf);
    Optional<ReportComment> findByCommentsMovieId(Long id);
}
