package com.learnhub.dto;

import lombok.*;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuizDTO {

    private Long id;

    private String title;

    private Long lessonId;

    private List<QuestionDTO> questions;
}