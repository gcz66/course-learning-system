package com.course.learning.service;

import com.course.learning.common.BusinessException;
import com.course.learning.entity.Chapter;
import com.course.learning.entity.StudyRecord;
import com.course.learning.repository.ChapterRepository;
import com.course.learning.repository.StudyRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 学习记录服务：记录章节学习进度
 */
@Service
@RequiredArgsConstructor
public class StudyService {

    private final StudyRecordRepository studyRecordRepository;
    private final ChapterRepository chapterRepository;

    /**
     * 上报学习进度
     * @param userId 用户
     * @param chapterId 章节
     * @param progress 进度 0-100
     */
    public StudyRecord reportProgress(Long userId, Long chapterId, Integer progress) {
        Chapter chapter = chapterRepository.findById(chapterId)
                .orElseThrow(() -> new BusinessException("章节不存在"));
        if (progress == null || progress < 0 || progress > 100) {
            throw new BusinessException(400, "进度需在 0-100 之间");
        }
        Optional<StudyRecord> exist = studyRecordRepository.findByUserIdAndChapterId(userId, chapterId);
        StudyRecord record = exist.orElseGet(StudyRecord::new);
        record.setUserId(userId);
        record.setCourseId(chapter.getCourseId());
        record.setChapterId(chapterId);
        record.setProgress(Math.max(record.getProgress() == null ? 0 : record.getProgress(), progress));
        record.setStatus(progress >= 100 ? 2 : (progress > 0 ? 1 : 0));
        return studyRecordRepository.save(record);
    }

    /** 用户在某课程下的学习记录 */
    public List<StudyRecord> recordsByCourse(Long userId, Long courseId) {
        return studyRecordRepository.findByUserIdAndCourseId(userId, courseId);
    }

    /** 用户全部学习记录 */
    public List<StudyRecord> allRecords(Long userId) {
        return studyRecordRepository.findByUserId(userId);
    }

    /** 计算课程学习进度（已学章节数 / 总章节数） */
    public Integer courseProgress(Long userId, Long courseId) {
        long total = chapterRepository.findByCourseIdOrderBySortOrderAsc(courseId).size();
        if (total == 0) return 0;
        long done = studyRecordRepository.findByUserIdAndCourseId(userId, courseId)
                .stream().filter(r -> r.getProgress() != null && r.getProgress() >= 100).count();
        return (int) Math.round(done * 100.0 / total);
    }
}
