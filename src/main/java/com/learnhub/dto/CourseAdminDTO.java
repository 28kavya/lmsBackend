package com.learnhub.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseAdminDTO {
    private Long id;
    private String title;
    private String description;
    private double price;
    private Long instructorId;
    private String instructor;
    private List<EnrollmentDTO> enrollments;
    private Integer students;

}
