package com.learnhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizResultResponse {

    private int score;

    private int totalQuestions;

    private double percentage;
}