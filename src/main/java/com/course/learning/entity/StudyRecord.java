package com.course.learning.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 学习记录实体
 * 记录用户对章节的学习进度
 */
@Data
@Entity
@Table(name = "t_study_record")
public class StudyRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 用户 id */
    private Long userId;

    /** 课程 id */
    private Long courseId;

    /** 章节 id */
    private Long chapterId;

    /** 学习状态：0 未学 / 1 学习中 / 2 已完成 */
    private Integer status = 0;

    /** 学习进度百分比 0-100 */
    private Integer progress = 0;

    /** 最后学习时间 */
    private LocalDateTime updateTime = LocalDateTime.now();
}
