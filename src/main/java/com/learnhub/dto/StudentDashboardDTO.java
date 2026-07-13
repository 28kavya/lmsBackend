package com.learnhub.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentDashboardDTO {

    private String studentName;

    private long enrolledCourses;

    private long certificates;

    private double overallProgress;

}