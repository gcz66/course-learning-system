package com.course.learning.controller;

import com.course.learning.common.Result;
import com.course.learning.entity.Score;
import com.course.learning.service.ScoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 成绩管理
 */
@RestController
@RequestMapping("/api/score")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    /** 录入 / 更新成绩（管理端） */
    @PostMapping
    public Result<Score> save(@RequestBody Map<String, Object> body) {
        Long userId = Long.valueOf(body.get("userId").toString());
        Long courseId = Long.valueOf(body.get("courseId").toString());
        String examName = body.get("examName") != null ? body.get("examName").toString() : null;
        Integer score = Integer.valueOf(body.get("score").toString());
        return Result.ok("成绩已保存", scoreService.save(userId, courseId, examName, score));
    }

    /** 某学生全部成绩 */
    @GetMapping("/user/{userId}")
    public Result<List<Score>> byUser(@PathVariable Long userId) {
        return Result.ok(scoreService.byUser(userId));
    }

    /** 某课程全部成绩 */
    @GetMapping("/course/{courseId}")
    public Result<List<Score>> byCourse(@PathVariable Long courseId) {
        return Result.ok(scoreService.byCourse(courseId));
    }

    /** 某学生某课程成绩统计（平均分等） */
    @GetMapping("/stats")
    public Result<Map<String, Object>> stats(@RequestParam Long userId, @RequestParam Long courseId) {
        return Result.ok(scoreService.stats(userId, courseId));
    }
}
