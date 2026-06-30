package com.learnhub.service;

import com.learnhub.entity.Question;
import com.learnhub.entity.Quiz;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.repository.QuestionRepository;
import com.learnhub.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class QuestionService {
    @Autowired
    private  QuestionRepository questionRepository;

    @Autowired
    private QuizRepository quizRepository;

    public Question addQuestion(Long quizId, Question question) {

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Quiz Not Found"));

        question.setQuiz(quiz);

        return questionRepository.save(question);
    }

    public List<Question> getQuestions(Long quizId) {
        return questionRepository.findByQuizId(quizId);
    }
}
