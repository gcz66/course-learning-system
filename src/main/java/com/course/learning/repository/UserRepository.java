package com.course.learning.repository;

import com.course.learning.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Optional<User> findByWxOpenid(String wxOpenid);

    List<User> findByRole(String role);

    List<User> findByClassId(Long classId);
}
