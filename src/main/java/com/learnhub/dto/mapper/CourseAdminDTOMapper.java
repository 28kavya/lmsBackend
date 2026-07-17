package com.learnhub.dto.mapper;

import com.learnhub.dto.CourseAdminDTO;
import com.learnhub.entity.Course;

import java.util.List;

public class CourseAdminDTOMapper {
    public static CourseAdminDTO mapToCourseAdminDTO(Course course) {

        return CourseAdminDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .price(course.getPrice())
                .instructorId(
                        course.getInstructor() != null
                                ? course.getInstructor().getId()
                                : null
                )
                .instructor(
                        course.getInstructor() != null
                                ? course.getInstructor().getName()
                                : null
                )
                .students(course.getEnrollments() != null
                        ? course.getEnrollments().size()
                        : 0)
                .enrollments(course.getEnrollments() != null
                        ? course.getEnrollments().stream()
                        .map(EnrollmentDTOMapper::mapToEnrollmentDTO)
                        .toList()
                        : List.of())
                .build();
    }
}