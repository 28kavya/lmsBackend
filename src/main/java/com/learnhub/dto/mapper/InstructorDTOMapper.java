package com.learnhub.dto.mapper;

import com.learnhub.dto.InstructorDTO;
import com.learnhub.entity.User;

public class InstructorDTOMapper {

    public static InstructorDTO map(User user) {

        int totalCourses = user.getCourses() == null ? 0 : user.getCourses().size();

        int totalStudents = user.getCourses() == null ? 0 :
                user.getCourses()
                        .stream()
                        .mapToInt(c -> c.getEnrollments().size())
                        .sum();

        return InstructorDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .courses(totalCourses)
                .students(totalStudents)
                .build();
    }
}
