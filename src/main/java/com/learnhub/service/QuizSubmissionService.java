package com.learnhub.service;

import com.learnhub.dto.*;
import com.learnhub.entity.*;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.exception.UserNotFoundException;
import com.learnhub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizSubmissionService {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final QuestionRepository questionRepository;
    private final StudentAnswerRepository studentAnswerRepository;
    private final QuizResultRepository quizResultRepository;
    private final LessonProgressService lessonProgressService;

    public QuizResultResponse submitQuiz(QuizSubmissionRequest request) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Student Not Found"));

        Quiz quiz = quizRepository.findById(request.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz Not Found"));

        int score = 0;

        for (AnswerRequest answerRequest : request.getAnswers()) {

            Question question = questionRepository.findById(answerRequest.getQuestionId())
                            .orElseThrow(() -> new ResourceNotFoundException("Question Not Found"));

            StudentAnswer studentAnswer = new StudentAnswer();

            studentAnswer.setStudent(student);

            studentAnswer.setQuestion(question);

            studentAnswer.setSelectedAnswer(answerRequest.getSelectedAnswer());

            studentAnswerRepository.save(studentAnswer);

            if (question.getCorrectAnswer().equalsIgnoreCase(answerRequest.getSelectedAnswer())) {
                score++;
            }
        }

        int totalQuestions = request.getAnswers().size();

        double percentage = ((double) score / totalQuestions) * 100;

        QuizResult quizResult = new QuizResult();

        quizResult.setStudent(student);
        quizResult.setQuiz(quiz);
        quizResult.setScore(score);
        quizResult.setTotalQuestions(totalQuestions);
        quizResult.setPercentage(percentage);

        quizResultRepository.save(quizResult);
        boolean passed = percentage >= 70;

        lessonProgressService.updateQuizStatus(
                quiz.getLesson().getId(),
                passed
        );
        return new QuizResultResponse(score, totalQuestions, percentage);
    }
}
