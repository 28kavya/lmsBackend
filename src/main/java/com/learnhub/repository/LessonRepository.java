package com.learnhub.repository;

import com.learnhub.dto.LessonDTO;
import com.learnhub.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByCourseId(Long courseId);
}
