package com.learnhub.repository;

import com.learnhub.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizRepository
        extends JpaRepository<Quiz, Long> {

    List<Quiz> findByLessonId(Long lessonId);
}