package com.learnhub.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StudentDTO {

    private Long id;
    private String name;
    private String email;

    private List<EnrollmentDTO> enrollments;
}
