package com.course.learning.repository;

import com.course.learning.entity.ClassInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassInfoRepository extends JpaRepository<ClassInfo, Long> {
}
