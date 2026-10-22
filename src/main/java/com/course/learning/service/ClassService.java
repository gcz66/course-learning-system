package com.course.learning.service;

import com.course.learning.common.BusinessException;
import com.course.learning.entity.ClassInfo;
import com.course.learning.repository.ClassInfoRepository;
import com.course.learning.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 班级服务
 */
@Service
@RequiredArgsConstructor
public class ClassService {

    private final ClassInfoRepository classInfoRepository;
    private final UserRepository userRepository;

    public List<ClassInfo> list() {
        return classInfoRepository.findAll();
    }

    public ClassInfo create(ClassInfo classInfo) {
        if (!StringUtils.hasText(classInfo.getName())) {
            throw new BusinessException(400, "班级名称不能为空");
        }
        return classInfoRepository.save(classInfo);
    }

    public ClassInfo update(Long id, ClassInfo classInfo) {
        ClassInfo exist = classInfoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("班级不存在"));
        if (StringUtils.hasText(classInfo.getName())) exist.setName(classInfo.getName());
        if (StringUtils.hasText(classInfo.getGrade())) exist.setGrade(classInfo.getGrade());
        if (StringUtils.hasText(classInfo.getTeacher())) exist.setTeacher(classInfo.getTeacher());
        return classInfoRepository.save(exist);
    }

    /** 删除班级：若仍有学生绑定则拒绝 */
    public void delete(Long id) {
        if (!classInfoRepository.existsById(id)) {
            throw new BusinessException("班级不存在");
        }
        if (!userRepository.findByClassId(id).isEmpty()) {
            throw new BusinessException("该班级下仍有学生，无法删除");
        }
        classInfoRepository.deleteById(id);
    }

    /** 某班级的学生列表 */
    public List<?> students(Long classId) {
        return userRepository.findByClassId(classId);
    }
}
