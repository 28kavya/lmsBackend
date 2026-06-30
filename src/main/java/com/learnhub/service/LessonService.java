package com.learnhub.service;

import com.learnhub.dto.LessonDTO;
import com.learnhub.dto.mapper.LessonDtoMapper;
import com.learnhub.entity.Course;
import com.learnhub.entity.Lesson;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.repository.CourseRepository;
import com.learnhub.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private  CourseRepository courseRepository;

    public LessonDTO addLesson(Long courseId, Lesson lesson) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));

        lesson.setCourse(course);
        Lesson l=lessonRepository.save(lesson);
        return LessonDtoMapper.mapToLessonDTO(l);
    }

    public List<LessonDTO> getLessonsByCourse(Long courseId) {
        List<Lesson> existingLesson= lessonRepository.findByCourseId(courseId);

        return existingLesson.stream().map(LessonDtoMapper::mapToLessonDTO).toList();
    }
}