package com.learnhub.controller;

import com.learnhub.dto.QuizResultResponse;
import com.learnhub.dto.QuizSubmissionRequest;
import com.learnhub.service.QuizSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quizsanswer")
@RequiredArgsConstructor
public class QuizSubmissionController {

    private final QuizSubmissionService quizSubmissionService;

    @PostMapping("/submit")
    public QuizResultResponse submitQuiz(@RequestBody QuizSubmissionRequest request) {

        return quizSubmissionService.submitQuiz(request);
    }
}