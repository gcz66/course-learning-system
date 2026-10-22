package com.course.learning.service;

import com.course.learning.common.BusinessException;
import com.course.learning.entity.Course;
import com.course.learning.entity.Chapter;
import com.course.learning.repository.CourseRepository;
import com.course.learning.repository.ChapterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 课程管理服务
 */
@Service
@RequiredArgsConstructor
public class CourseService {

    private final CourseRepository courseRepository;
    private final ChapterRepository chapterRepository;

    /** 创建课程 */
    public Course create(Course course) {
        if (!StringUtils.hasText(course.getName())) {
            throw new BusinessException(400, "课程名称不能为空");
        }
        return courseRepository.save(course);
    }

    /** 更新课程 */
    @Transactional
    public Course update(Long id, Course course) {
        Course exist = courseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("课程不存在"));
        if (StringUtils.hasText(course.getName())) exist.setName(course.getName());
        if (StringUtils.hasText(course.getDescription())) exist.setDescription(course.getDescription());
        if (StringUtils.hasText(course.getTeacher())) exist.setTeacher(course.getTeacher());
        if (StringUtils.hasText(course.getCover())) exist.setCover(course.getCover());
        if (StringUtils.hasText(course.getCategory())) exist.setCategory(course.getCategory());
        return courseRepository.save(exist);
    }

    /** 删除课程（级联删除章节） */
    @Transactional
    public void delete(Long id) {
        Course exist = courseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("课程不存在"));
        chapterRepository.deleteAll(chapterRepository.findByCourseIdOrderBySortOrderAsc(id));
        courseRepository.delete(exist);
    }

    /** 课程列表（可选分类 / 关键词过滤） */
    public List<Course> list(String category, String keyword) {
        if (StringUtils.hasText(category)) {
            return courseRepository.findByCategory(category);
        }
        if (StringUtils.hasText(keyword)) {
            return courseRepository.findByNameContaining(keyword);
        }
        return courseRepository.findAll();
    }

    /** 课程详情（含章节列表） */
    public Course detail(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new BusinessException("课程不存在"));
    }

    /** 课程章节列表 */
    public List<Chapter> chapters(Long courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new BusinessException("课程不存在");
        }
        return chapterRepository.findByCourseIdOrderBySortOrderAsc(courseId);
    }

    /** 添加章节 */
    public Chapter addChapter(Chapter chapter) {
        if (!courseRepository.existsById(chapter.getCourseId())) {
            throw new BusinessException("课程不存在");
        }
        if (!StringUtils.hasText(chapter.getTitle())) {
            throw new BusinessException(400, "章节标题不能为空");
        }
        if (chapter.getSortOrder() == null) {
            chapter.setSortOrder(1);
        }
        return chapterRepository.save(chapter);
    }

    /** 更新章节 */
    public Chapter updateChapter(Long id, Chapter chapter) {
        Chapter exist = chapterRepository.findById(id)
                .orElseThrow(() -> new BusinessException("章节不存在"));
        if (StringUtils.hasText(chapter.getTitle())) exist.setTitle(chapter.getTitle());
        if (chapter.getContent() != null) exist.setContent(chapter.getContent());
        if (chapter.getSortOrder() != null) exist.setSortOrder(chapter.getSortOrder());
        return chapterRepository.save(exist);
    }

    /** 删除章节 */
    public void deleteChapter(Long id) {
        if (!chapterRepository.existsById(id)) {
            throw new BusinessException("章节不存在");
        }
        chapterRepository.deleteById(id);
    }
}
