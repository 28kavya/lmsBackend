package com.learnhub.service;

import com.learnhub.dto.MyCourseDTO;
import com.learnhub.dto.StudentDashboardDTO;
import com.learnhub.entity.Course;
import com.learnhub.entity.Enrollment;
import com.learnhub.entity.User;
import com.learnhub.repository.CertificateRepository;
import com.learnhub.repository.EnrollmentRepository;
import com.learnhub.repository.LessonProgressRepository;
import com.learnhub.repository.LessonRepository;
import com.learnhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentDashboardService {

    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final CertificateRepository certificateRepository;
    private final LessonRepository lessonRepository;
    private final LessonProgressRepository lessonProgressRepository;


    public StudentDashboardDTO getDashboard(String email) {

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Long studentId = student.getId();

        // Use studentId for all your queries

        Long enrolledCourses = enrollmentRepository.countByStudentId(studentId);

        StudentDashboardDTO dto = new StudentDashboardDTO();
        dto.setStudentName(student.getName());
        dto.setEnrolledCourses(enrolledCourses);

        return dto;
    }

    public List<MyCourseDTO> getMyCourses(Long studentId) {

        List<Enrollment> enrollments =
                enrollmentRepository.findByStudentId(studentId);

        List<MyCourseDTO> courses = new ArrayList<>();

        for (Enrollment enrollment : enrollments) {

            Course course = enrollment.getCourse();

            Long completedLessons =
                    lessonProgressRepository.countCompletedLessons(
                            studentId,
                            course.getId());

            long totalLessons =
                    lessonRepository.countByCourseId(course.getId());

            double progress = 0;

            if (totalLessons > 0) {
                progress = (completedLessons * 100.0) / totalLessons;
            }
            courses.add(
                    new MyCourseDTO(
                            course.getId(),
                            course.getTitle(),
                            course.getDescription(),
                            course.getInstructor().toString(),
                            course.getPrice(),
                            progress
                    )
            );
        }
        return courses;
    }
}