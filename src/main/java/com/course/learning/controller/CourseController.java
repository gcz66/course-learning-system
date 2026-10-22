package com.course.learning.controller;

import com.course.learning.common.Result;
import com.course.learning.entity.Course;
import com.course.learning.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程管理（公开查询，增删改走管理端）
 */
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    /** 课程列表：/api/course/list?category=xx&keyword=xx */
    @GetMapping("/list")
    public Result<List<Course>> list(@RequestParam(required = false) String category,
                                     @RequestParam(required = false) String keyword) {
        return Result.ok(courseService.list(category, keyword));
    }

    /** 课程详情 */
    @GetMapping("/{id}")
    public Result<Course> detail(@PathVariable Long id) {
        return Result.ok(courseService.detail(id));
    }

    /** 创建课程（管理端） */
    @PostMapping
    public Result<Course> create(@RequestBody Course course) {
        return Result.ok("创建成功", courseService.create(course));
    }

    /** 更新课程（管理端） */
    @PutMapping("/{id}")
    public Result<Course> update(@PathVariable Long id, @RequestBody Course course) {
        return Result.ok("更新成功", courseService.update(id, course));
    }

    /** 删除课程（管理端，级联删除章节） */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        courseService.delete(id);
        return Result.ok("删除成功", null);
    }
}
