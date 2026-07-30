package com.learnhub.controller;

import com.learnhub.dto.CertificateDTO;
import com.learnhub.service.CertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
@RequiredArgsConstructor
public class CertificateController {

    private final CertificateService certificateService;

    @GetMapping("/download/{courseId}")
    public ResponseEntity<byte[]> downloadCertificate(
            @PathVariable Long courseId) {

        byte[] pdf = certificateService.generateCertificatePdf(courseId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=LearnHub_Certificate.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    @GetMapping
    public List<CertificateDTO> getCertificates() {
        return certificateService.generateCertificatesByStudent();
    }

}