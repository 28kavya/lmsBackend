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

    public Lesson addLesson(LessonDTO dto){

        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() ->
                        new RuntimeException("Course not found"));

        Lesson lesson = new Lesson();
        System.out.println("====== LESSON DTO ======");
        System.out.println("Title        : " + dto.getTitle());
        System.out.println("Description  : " + dto.getDescription());
        System.out.println("Lesson Order : " + dto.getLessonOrder());
        System.out.println("Video URL    : " + dto.getVideoUrl());
        System.out.println("Course ID    : " + dto.getCourseId());

        lesson.setTitle(dto.getTitle());
        lesson.setDescription(dto.getDescription());
        lesson.setVideoUrl(dto.getVideoUrl());
        lesson.setLessonOrder(dto.getLessonOrder());
        lesson.setCourse(course);

        return lessonRepository.save(lesson);
    }
    public List<LessonDTO> getLessonsByCourse(Long courseId) {
        List<Lesson> existingLesson= lessonRepository.findByCourseId(courseId);

        return existingLesson.stream().map(LessonDtoMapper::mapToLessonDTO).toList();
    }
}