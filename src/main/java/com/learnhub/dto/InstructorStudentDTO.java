package com.learnhub.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InstructorStudentDTO {

    private Long id;
    private String name;
    private String email;
    private String course;
    private String status;
}
