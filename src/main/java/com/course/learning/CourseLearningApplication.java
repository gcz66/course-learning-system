package com.course.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 课程学习系统后端启动类
 * 为课程学习小程序提供 RESTful API
 */
@SpringBootApplication
public class CourseLearningApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourseLearningApplication.class, args);
    }
}
