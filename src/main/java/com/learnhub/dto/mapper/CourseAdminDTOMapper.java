package com.learnhub.dto.mapper;

import com.learnhub.dto.CourseAdminDTO;
import com.learnhub.entity.Course;

public class CourseAdminDTOMapper {

    public static CourseAdminDTO mapToCourseAdminDTO(Course course) {

        return CourseAdminDTO.builder()
                .id(course.getId())
                .title(course.getTitle())
                .description(course.getDescription())
                .price(course.getPrice())
                .instructor(course.getInstructor())
                .enrollments(
                        course.getEnrollments()
                                .stream()
                                .map(EnrollmentDTOMapper::mapToEnrollmentDTO)
                                .toList()
                )
                .build();
    }
}