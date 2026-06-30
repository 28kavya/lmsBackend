package com.learnhub.dto.mapper;

import com.learnhub.dto.CourseDTO;
import com.learnhub.entity.Course;

import java.util.List;

public class CourseDTOMapper {
    public static CourseDTO mapToCourseDTO(Course course){
       return CourseDTO.builder()
               .id(course.getId())
               .title(course.getTitle())
               .price(course.getPrice())
               .description(course.getDescription())
               .instructor(course.getInstructor())
               .lessons(course.getLessons()!=null?course.getLessons().stream().map(LessonDtoMapper::mapToLessonDTO).toList(): List.of())
               .build();
    }
}
