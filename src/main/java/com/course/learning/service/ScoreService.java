package com.course.learning.service;

import com.course.learning.common.BusinessException;
import com.course.learning.entity.Score;
import com.course.learning.repository.CourseRepository;
import com.course.learning.repository.ScoreRepository;
import com.course.learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 成绩服务：成绩录入、查询、统计与计算
 */
@Service
@RequiredArgsConstructor
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    /** 录入 / 更新成绩（同一用户同一课程同一考试，重复录入则更新） */
    public Score save(Long userId, Long courseId, String examName, Integer score) {
        if (!userRepository.existsById(userId)) {
            throw new BusinessException("学生不存在");
        }
        if (!courseRepository.existsById(courseId)) {
            throw new BusinessException("课程不存在");
        }
        if (score == null || score < 0 || score > 100) {
            throw new BusinessException(400, "分数需在 0-100 之间");
        }
        Optional<Score> exist = scoreRepository.findByUserIdAndCourseIdAndExamName(userId, courseId, examName);
        Score s = exist.orElseGet(Score::new);
        s.setUserId(userId);
        s.setCourseId(courseId);
        s.setExamName(examName == null || examName.isBlank() ? "平时成绩" : examName);
        s.setScore(score);
        return scoreRepository.save(s);
    }

    /** 查询某学生全部成绩 */
    public List<Score> byUser(Long userId) {
        return scoreRepository.findByUserId(userId);
    }

    /** 查询某课程全部成绩 */
    public List<Score> byCourse(Long courseId) {
        return scoreRepository.findByCourseId(courseId);
    }

    /** 某学生某课程平均分 */
    public Map<String, Object> stats(Long userId, Long courseId) {
        List<Score> list = scoreRepository.findByUserIdAndCourseId(userId, courseId);
        Map<String, Object> map = new HashMap<>();
        if (list.isEmpty()) {
            map.put("count", 0);
            map.put("average", 0);
            map.put("scores", list);
            return map;
        }
        double avg = list.stream().mapToInt(Score::getScore).average().orElse(0);
        map.put("count", list.size());
        map.put("average", Math.round(avg * 10) / 10.0);
        map.put("scores", list);
        return map;
    }
}
