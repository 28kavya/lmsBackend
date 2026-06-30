package com.learnhub.dto.mapper;

import com.learnhub.dto.AdminQuestionDTO;
import com.learnhub.dto.QuestionDTO;
import com.learnhub.entity.Question;

public class AdminQuestionDTOMapper {
    public static AdminQuestionDTO mapToDTO(Question question) {
        return AdminQuestionDTO.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .correctAnswer(question.getCorrectAnswer())
                .build();
    }
}
