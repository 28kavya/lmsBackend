package com.learnhub.dto.mapper;

import com.learnhub.dto.QuestionDTO;
import com.learnhub.dto.QuizDTO;
import com.learnhub.entity.Question;
import com.learnhub.entity.Quiz;

import java.util.List;


public class QuizDtoMapper {

    public static QuizDTO mapToDto(Quiz quiz) {

        return QuizDTO.builder()
                .id(quiz.getId())
                .title(quiz.getTitle())
                .lessonId(quiz.getLesson()!=null?quiz.getLesson().getId():null)
                .questions(quiz.getQuestions()!=null?quiz.getQuestions().stream().map(question -> {
                    return QuestionDTO.builder()
                            .id(question.getId())
                            .questionText(question.getQuestionText())
                            .optionA(question.getOptionA())
                            .optionB(question.getOptionB())
                            .optionC(question.getOptionC())
                            .optionD(question.getOptionD()).build();
                }).toList(): List.of())

                .build();

//        QuizDTO quizDTO = new QuizDTO();
//        quizDTO.setId(quiz.getId());
//        quizDTO.setTitle(quiz.getTitle());
//        quizDTO.setQuestions(quiz.getQuestions().stream().map(question -> {
//            QuestionDTO questionDTO = new QuestionDTO();
//            questionDTO.setId(question.getId());
//            questionDTO.setQuestionText(question.getQuestionText());
//            questionDTO.setOptionA(question.getOptionA());
//            questionDTO.setOptionB(question.getOptionB());
//            questionDTO.setOptionC(question.getOptionC());
//            questionDTO.setOptionD(question.getOptionD());
//            return questionDTO;
//        }).toList());
//
//        return quizDTO;

    }
}
