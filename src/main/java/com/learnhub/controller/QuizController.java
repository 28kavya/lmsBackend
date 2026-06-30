package com.learnhub.controller;

import com.learnhub.dto.QuizDTO;
import com.learnhub.entity.Quiz;
import com.learnhub.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/quiz")

public class QuizController {
    @Autowired
    private  QuizService quizService;

    @PostMapping("/lesson/{lessonId}")
    public QuizDTO addQuiz(@PathVariable Long lessonId, @RequestBody Quiz quiz) {

        return quizService.addQuiz(lessonId, quiz);
    }

    @GetMapping("/lesson/{lessonId}")
    public List<Quiz> getQuizzes(@PathVariable Long lessonId) {
        return quizService.getQuizzesByLesson(lessonId);
    }
}
