package com.course.learning.repository;

import com.course.learning.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {

    List<Score> findByUserId(Long userId);

    List<Score> findByCourseId(Long courseId);

    List<Score> findByUserIdAndCourseId(Long userId, Long courseId);

    Optional<Score> findByUserIdAndCourseIdAndExamName(Long userId, Long courseId, String examName);
}
