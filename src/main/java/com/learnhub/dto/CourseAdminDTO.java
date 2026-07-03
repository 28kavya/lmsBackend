package com.learnhub.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class CourseAdminDTO {
    private Long id;
    private String title;
    private String description;
    private double price;
    private String instructor;
    private List<EnrollmentDTO> enrollments;

}
