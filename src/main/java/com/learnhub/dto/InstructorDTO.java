package com.learnhub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InstructorDTO {

    private Long id;
    private String name;
    private String email;
    private int courses;
    private int students;
}
