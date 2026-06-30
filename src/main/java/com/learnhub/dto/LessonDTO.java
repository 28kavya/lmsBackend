package com.learnhub.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LessonDTO {

    private Long id;

    private String title;

    private String description;

    private String videoUrl;

    private Long courseId;

    private List<QuizDTO> quizzes;
}
