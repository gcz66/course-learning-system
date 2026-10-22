package com.course.learning.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 成绩实体
 * 记录某学生某课程的一次考试成绩
 */
@Data
@Entity
@Table(name = "t_score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户 id */
    private Long userId;

    /** 课程 id */
    private Long courseId;

    /** 考试名称（如：期中考试 / 期末考试） */
    private String examName;

    /** 分数（0-100） */
    private Integer score;

    /** 创建时间 */
    private LocalDateTime createTime = LocalDateTime.now();
}
