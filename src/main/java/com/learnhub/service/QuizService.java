package com.learnhub.service;

import com.learnhub.dto.QuizDTO;
import com.learnhub.dto.mapper.QuizDtoMapper;
import com.learnhub.entity.Lesson;
import com.learnhub.entity.Quiz;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.repository.LessonRepository;
import com.learnhub.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;
    private final LessonRepository lessonRepository;

    public QuizDTO addQuiz(Long lessonId, Quiz quiz) {

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException("Lesson Not Found"));

        quiz.setLesson(lesson);
        if(quiz.getQuestions()!=null) {
            quiz.getQuestions().forEach(question -> question.setQuiz(quiz));
            System.out.println("Questions received: " + quiz.getQuestions());
        }

        Quiz savedQuiz= quizRepository.save(quiz);

        return QuizDtoMapper.mapToDto(savedQuiz);

    }


    public List<Quiz> getQuizzesByLesson(Long lessonId) {
        return quizRepository.findByLessonId(lessonId);
    }
}
