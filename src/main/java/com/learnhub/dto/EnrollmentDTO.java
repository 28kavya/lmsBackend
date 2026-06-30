package com.learnhub.dto;

import lombok.*;

@Data
@Builder
public class EnrollmentDTO {

    private Long id;

    private Long studentId;
    private String studentName;

    private Long courseId;
    private String courseTitle;

    private String status;
}