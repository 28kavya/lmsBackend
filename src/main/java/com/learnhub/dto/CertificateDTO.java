package com.learnhub.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO {

    private Long id;

    private String certificateNumber;

    private LocalDate issuedDate;

    private Long studentId;

    private String studentName;

    private Long courseId;

    private String courseTitle;
}
