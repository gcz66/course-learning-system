package com.course.learning.controller;

import com.course.learning.common.Result;
import com.course.learning.entity.Chapter;
import com.course.learning.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 章节学习管理
 */
@RestController
@RequestMapping("/api/chapter")
@RequiredArgsConstructor
public class ChapterController {

    private final CourseService courseService;

    /** 某课程的章节列表 */
    @GetMapping("/list/{courseId}")
    public Result<List<Chapter>> list(@PathVariable Long courseId) {
        return Result.ok(courseService.chapters(courseId));
    }

    /** 添加章节（管理端） */
    @PostMapping
    public Result<Chapter> add(@RequestBody Chapter chapter) {
        return Result.ok("添加成功", courseService.addChapter(chapter));
    }

    /** 更新章节（管理端） */
    @PutMapping("/{id}")
    public Result<Chapter> update(@PathVariable Long id, @RequestBody Chapter chapter) {
        return Result.ok("更新成功", courseService.updateChapter(id, chapter));
    }

    /** 删除章节（管理端） */
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        courseService.deleteChapter(id);
        return Result.ok("删除成功", null);
    }
}
