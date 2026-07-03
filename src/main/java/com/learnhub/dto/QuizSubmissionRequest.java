package com.learnhub.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuizSubmissionRequest {
    private Long quizId;

    private List<AnswerRequest> answers;
}
