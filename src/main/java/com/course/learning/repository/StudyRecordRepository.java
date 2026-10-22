package com.course.learning.repository;

import com.course.learning.entity.StudyRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudyRecordRepository extends JpaRepository<StudyRecord, Long> {

    Optional<StudyRecord> findByUserIdAndChapterId(Long userId, Long chapterId);

    List<StudyRecord> findByUserId(Long userId);

    List<StudyRecord> findByUserIdAndCourseId(Long userId, Long courseId);

    /** 统计某课程的学习人数 */
    long countByCourseId(Long courseId);
}
