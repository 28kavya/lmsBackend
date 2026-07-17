package com.learnhub.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {

    private Long id;

    private String title;

    private String description;

    private double price;

    private String instructor;
    private int students;



}