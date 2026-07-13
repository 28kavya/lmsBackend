package com.learnhub.repository;

import com.learnhub.entity.LessonProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LessonProgressRepository
        extends JpaRepository<LessonProgress,Long>{

    Optional<LessonProgress> findByUserIdAndLessonId(
            Long userId,
            Long lessonId
    );

    long countByUserIdAndLessonCourseIdAndVideoCompletedTrueAndQuizPassedTrue(
            Long userId,
            Long courseId
    );
    @Query("""
SELECT COUNT(lp)
FROM LessonProgress lp
WHERE lp.user.id = :studentId
AND lp.lesson.course.id = :courseId
AND lp.videoCompleted = true
AND lp.quizPassed = true
""")
    Long countCompletedLessons(Long studentId, Long courseId);

    long countByUserId(Long userId);

    long countByUserIdAndVideoCompletedTrueAndQuizPassedTrue(Long userId);


}