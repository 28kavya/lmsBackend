    package com.learnhub.controller;

    import com.learnhub.entity.Question;
    import com.learnhub.service.QuestionService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    import java.util.List;

    @RestController
    @RequestMapping("/api/questions")
    public class QuestionController {

        @Autowired
        private QuestionService questionService;

        @PostMapping("/{quizId}")
        public Question addQuestion(@PathVariable Long quizId, @RequestBody Question question) {

            return questionService.addQuestion(quizId, question);
        }

        @GetMapping("/quiz/{quizId}")
        public List<Question> getQuestions(@PathVariable Long quizId) {

            return questionService.getQuestions(quizId);
        }
    }
