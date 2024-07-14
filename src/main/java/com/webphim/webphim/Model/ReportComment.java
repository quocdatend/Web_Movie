package com.webphim.webphim.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "REPORT_COMMENT")
public class ReportComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "commentLevelId")
    private CommentLevel commentLevel;
    @OneToOne
    @JoinColumn(name = "commentsMovieId")
    private CommentsMovie commentsMovie;
    @ManyToOne
    @JoinColumn(name = "usersId", nullable = false)
    private Users users;
    @Column(nullable = false)
    private LocalDateTime time;
}