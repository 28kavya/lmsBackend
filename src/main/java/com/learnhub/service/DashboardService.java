package com.learnhub.service;

import com.learnhub.dto.DashboardDTO;
import com.learnhub.entity.Roles;
import com.learnhub.repository.CourseRepository;
import com.learnhub.repository.EnrollmentRepository;
import com.learnhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final EnrollmentRepository enrollmentRepository;

    public DashboardDTO getDashboardStats() {

        long totalStudents =
                userRepository.countByRole(Roles.STUDENT);

        long totalInstructors =
                userRepository.countByRole(Roles.INSTRUCTOR);

        long totalCourses =
                courseRepository.count();

        Double revenue =
                enrollmentRepository.getTotalRevenue();

        if (revenue == null) {
            revenue = 0.0;
        }

        return new DashboardDTO(
                totalStudents,
                totalInstructors,
                totalCourses,
                revenue
        );

    }

}
