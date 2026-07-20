package com.learnhub.dto.mapper;

import com.learnhub.dto.StudentDTO;
import com.learnhub.entity.User;

public class StudentDtoMapper {

    public static StudentDTO map(User user) {

        return StudentDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .enrollments(
                        user.getEnrollments()
                                .stream()
                                .map(EnrollmentDTOMapper::mapToEnrollmentDTO)
                                .toList()
                )
                .build();
    }
}