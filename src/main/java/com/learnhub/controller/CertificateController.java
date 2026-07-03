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

    @PostMapping("/generate/{courseId}")
    public CertificateDTO generateCertificate( @PathVariable Long courseId){

        return certificateService.generateCertificate(courseId);
    }

    @GetMapping("/my")
    public List<CertificateDTO> getStudentCertificates(){

        return certificateService.getCertificatesByStudent();
    }
}
