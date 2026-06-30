package com.learnhub.dto.mapper;

import com.learnhub.dto.EnrollmentDTO;
import com.learnhub.entity.Enrollment;

public class EnrollmentDTOMapper {
    public static EnrollmentDTO mapToEnrollmentDTO(Enrollment enrollment) {
        return EnrollmentDTO.builder()
                .id(enrollment.getId())
                .studentId(enrollment.getStudent().getId())
                .studentName(enrollment.getStudent().getName())
                .courseId(enrollment.getCourse().getId())
                .courseTitle(enrollment.getCourse().getTitle())
                .status(enrollment.getStatus())
                .build();

    }
}
