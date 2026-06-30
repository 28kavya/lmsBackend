package com.learnhub.repository;

import com.learnhub.entity.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentAnswerRepository
        extends JpaRepository<StudentAnswer, Long> {
}