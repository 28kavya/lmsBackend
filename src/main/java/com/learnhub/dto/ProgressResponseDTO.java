package com.learnhub.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProgressResponseDTO {

    private Long studentId;

    private Long courseId;

    private double percentage;

    private String status;
}