package com.learnhub.dto.mapper;

import com.learnhub.dto.ProgressResponseDTO;
import com.learnhub.entity.Progress;

public class ProgressDTOMapper {

    public static ProgressResponseDTO
    mapToProgressResponseDTO(Progress progress){

        return ProgressResponseDTO.builder()
                .studentId(progress.getStudent().getId())
                .courseId(progress.getCourse().getId())
                .status(progress.getStatus())
                .percentage(progress.getPercentage())
                .build();

    }
}