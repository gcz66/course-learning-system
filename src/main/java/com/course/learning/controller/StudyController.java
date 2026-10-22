package com.course.learning.controller;

import com.course.learning.common.Result;
import com.course.learning.entity.StudyRecord;
import com.course.learning.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 学习进度
 */
@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    /** 上报章节学习进度 */
    @PostMapping("/report")
    public Result<StudyRecord> report(@RequestAttribute("userId") Long userId,
                                      @RequestBody Map<String, Object> body) {
        Long chapterId = Long.valueOf(body.get("chapterId").toString());
        Integer progress = body.get("progress") != null
                ? Integer.valueOf(body.get("progress").toString()) : 0;
        return Result.ok("学习进度已保存", studyService.reportProgress(userId, chapterId, progress));
    }

    /** 某课程的学习记录 */
    @GetMapping("/course/{courseId}")
    public Result<List<StudyRecord>> byCourse(@RequestAttribute("userId") Long userId,
                                              @PathVariable Long courseId) {
        return Result.ok(studyService.recordsByCourse(userId, courseId));
    }

    /** 我的全部学习记录 */
    @GetMapping("/mine")
    public Result<List<StudyRecord>> mine(@RequestAttribute("userId") Long userId) {
        return Result.ok(studyService.allRecords(userId));
    }

    /** 课程学习进度（百分比） */
    @GetMapping("/progress/{courseId}")
    public Result<Integer> progress(@RequestAttribute("userId") Long userId,
                                    @PathVariable Long courseId) {
        return Result.ok(studyService.courseProgress(userId, courseId));
    }
}
