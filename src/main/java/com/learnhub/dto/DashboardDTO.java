package com.learnhub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {

    private long totalStudents;
    private long totalInstructors;
    private long totalCourses;
    private double totalRevenue;

}
