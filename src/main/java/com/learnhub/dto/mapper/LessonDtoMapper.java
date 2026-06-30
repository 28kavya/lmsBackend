package com.learnhub.dto.mapper;

import com.learnhub.dto.LessonDTO;
import com.learnhub.dto.QuizDTO;
import com.learnhub.entity.Lesson;

import java.util.List;

public class LessonDtoMapper {
    public static LessonDTO mapToLessonDTO(Lesson lesson) {
        return LessonDTO.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .description(lesson.getDescription())
                .videoUrl(lesson.getVideoUrl())
                .courseId(lesson.getCourse().getId())
                .quizzes(lesson.getQuizzes()!=null?lesson.getQuizzes().stream().map(QuizDtoMapper::mapToDto).toList(): List.of())
                .build();

    }
}
