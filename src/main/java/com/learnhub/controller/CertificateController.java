package com.learnhub.controller;

import com.learnhub.dto.CertificateDTO;
import com.learnhub.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificate")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @PostMapping("/generate/{studentId}/{courseId}")
    public CertificateDTO generateCertificate(@PathVariable Long studentId, @PathVariable Long courseId){

        return certificateService.generateCertificate(studentId, courseId);
    }

    @GetMapping("/student/{studentId}")
    public List<CertificateDTO> getStudentCertificates(@PathVariable Long studentId){

        return certificateService.getCertificatesByStudent(studentId);
    }
}
