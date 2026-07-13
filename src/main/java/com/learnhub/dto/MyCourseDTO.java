package com.learnhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyCourseDTO {

    private Long courseId;

    private String title;

    private String description;

    private String instructor;

    private double price;
    private Double progress;
}