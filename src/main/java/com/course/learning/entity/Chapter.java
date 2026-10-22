package com.course.learning.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 课程章节实体
 */
@Data
@Entity
@Table(name = "t_chapter")
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 所属课程 id */
    private Long courseId;

    /** 章节标题 */
    private String title;

    /** 章节内容（富文本/纯文本） */
    @Column(length = 10000)
    private String content;

    /** 排序号 */
    private Integer sortOrder;

    /** 创建时间 */
    private LocalDateTime createTime = LocalDateTime.now();
}
