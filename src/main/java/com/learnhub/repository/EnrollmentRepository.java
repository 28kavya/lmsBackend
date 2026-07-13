package com.learnhub.repository;

import com.learnhub.entity.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    @Query("""
            SELECT COALESCE(SUM(c.price),0)
            FROM Enrollment e
            JOIN e.course c
            """)
    Double getTotalRevenue();

    long countByStudentId(Long studentId);
    List<Enrollment> findByStudentId(Long studentId);
    long countByStudentIdAndStatus(Long studentId,String status);

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

}