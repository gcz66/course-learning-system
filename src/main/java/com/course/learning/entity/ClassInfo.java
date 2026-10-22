package com.course.learning.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 班级实体
 */
@Data
@Entity
@Table(name = "t_class")
public class ClassInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 班级名称 */
    private String name;

    /** 年级 */
    private String grade;

    /** 班主任 */
    private String teacher;

    /** 创建时间 */
    private LocalDateTime createTime = LocalDateTime.now();
}
