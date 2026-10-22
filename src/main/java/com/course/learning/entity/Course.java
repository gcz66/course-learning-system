package com.course.learning.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 课程实体
 */
@Data
@Entity
@Table(name = "t_course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 课程名称 */
    private String name;

    /** 课程简介 */
    @Column(length = 2000)
    private String description;

    /** 授课教师 */
    private String teacher;

    /** 封面图 */
    private String cover;

    /** 课程分类 */
    private String category;

    /** 创建时间 */
    private LocalDateTime createTime = LocalDateTime.now();
}
