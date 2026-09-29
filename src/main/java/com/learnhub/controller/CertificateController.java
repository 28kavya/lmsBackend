package com.learnhub.controller;

import com.learnhub.dto.CertificateDTO;
import com.learnhub.response.QrCodeUtil;
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
    @GetMapping("/verify/{certificateNumber}")
    public ResponseEntity<?> verifyCertificate(
            @PathVariable String certificateNumber) {

        return ResponseEntity.ok(
                certificateService.verifyCertificate(
                        certificateNumber
                )
        );
    }
    @GetMapping("/qr/{certificateNumber}")
    public ResponseEntity<byte[]> generateQr(
            @PathVariable String certificateNumber)
            throws Exception {

        String verifyUrl =
                "http://localhost:4200/verify?cert="
                        + certificateNumber;

        byte[] qr =
                QrCodeUtil.generateQrCode(
                        verifyUrl,
                        250,
                        250
                );

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qr);
    }

}