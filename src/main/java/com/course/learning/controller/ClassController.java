package com.course.learning.controller;

import com.course.learning.common.Result;
import com.course.learning.entity.ClassInfo;
import com.course.learning.service.ClassService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 班级管理
 */
@RestController
@RequestMapping("/api/class")
@RequiredArgsConstructor
public class ClassController {

    private final ClassService classService;

    /** 班级列表 */
    @GetMapping("/list")
    public Result<List<ClassInfo>> list() {
        return Result.ok(classService.list());
    }

    /** 创建班级（管理端） */
    @PostMapping
    public Result<ClassInfo> create(@RequestBody ClassInfo classInfo) {
        return Result.ok("创建成功", classService.create(classInfo));
    }

    /** 更新班级（管理端） */
    @PutMapping("/{id}")
    public Result<ClassInfo> update(@PathVariable Long id, @RequestBody ClassInfo classInfo) {
        return Result.ok("更新成功", classService.update(id, classInfo));
    }

    /** 删除班级（管理端，有学生时拒绝） */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        classService.delete(id);
        return Result.ok("删除成功", null);
    }

    /** 班级学生列表 */
    @GetMapping("/{id}/students")
    public Result<List<?>> students(@PathVariable Long id) {
        return Result.ok(classService.students(id));
    }
}
