package com.learnhub.repository;

import com.learnhub.entity.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizResultRepository
        extends JpaRepository<QuizResult, Long> {
    Optional<QuizResult>
    findTopByStudentIdOrderByPercentageDesc(
            Long studentId
    );
}