package com.learnhub.repository;

import com.learnhub.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    List<Lesson> findByCourseId(Long courseId);

    Optional<Lesson> findByCourseIdAndLessonOrder(Long courseId, Integer lessonOrder);

    long countByCourseId(Long courseId);
    @Query("""
SELECT COUNT(l)
FROM Lesson l
JOIN Enrollment e
ON l.course.id=e.course.id
WHERE e.student.id=:studentId
""")
    long countLessonsOfStudent(Long studentId);

}
