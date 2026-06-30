package com.learnhub.repository;

import com.learnhub.entity.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgressRepository
        extends JpaRepository<Progress, Long> {

    Optional<Progress> findByStudentIdAndCourseId(Long studentId, Long courseId);
}