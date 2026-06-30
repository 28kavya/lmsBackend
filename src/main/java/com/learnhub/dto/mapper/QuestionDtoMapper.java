package com.learnhub.dto.mapper;

import com.learnhub.dto.QuestionDTO;
import com.learnhub.entity.Question;

public class QuestionDtoMapper {
    public static QuestionDTO questionDtoToQuestionDTO(Question question) {
        return QuestionDTO.builder()
                .id(question.getId())
                .questionText(question.getQuestionText())
                .optionA(question.getOptionA())
                .optionB(question.getOptionB())
                .optionC(question.getOptionC())
                .optionD(question.getOptionD())
                .build();
    }
}
