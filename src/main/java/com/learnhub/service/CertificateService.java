package com.learnhub.service;

import com.learnhub.dto.CertificateDTO;
import com.learnhub.dto.mapper.CertificateDTOMapper;
import java.util.List;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.awt.Color;
import com.learnhub.entity.Certificate;
import com.learnhub.entity.Course;
import com.learnhub.entity.User;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.exception.UserNotFoundException;
import com.learnhub.repository.CertificateRepository;
import com.learnhub.repository.CourseRepository;
import com.learnhub.repository.UserRepository;
import com.learnhub.response.QrCodeUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final CertificateRepository certificateRepository;
    public Certificate createCertificate(
            User student,
            Course course
    ) {

        // Prevent duplicate certificate
        if (certificateRepository.existsByStudentIdAndCourseId(
                student.getId(),
                course.getId())) {

            return certificateRepository
                    .findByStudentIdAndCourseId(
                            student.getId(),
                            course.getId()
                    )
                    .orElseThrow();
        }

        Certificate certificate = new Certificate();

        certificate.setCertificateNumber(
                UUID.randomUUID().toString()
        );

        certificate.setIssuedDate(LocalDate.now());

        certificate.setStudent(student);

        certificate.setCourse(course);

        return certificateRepository.save(certificate);
    }
    public Map<String, Object> verifyCertificate(
            String certificateNumber) {

        Optional<Certificate> optionalCertificate =
                certificateRepository
                        .findByCertificateNumber(certificateNumber);

        if (optionalCertificate.isEmpty()) {

            return Map.of(
                    "valid", false,
                    "message", "Certificate not found"
            );
        }

        Certificate certificate =
                optionalCertificate.get();

        return Map.of(
                "valid", true,
                "certificateNumber",
                certificate.getCertificateNumber(),

                "studentName",
                certificate.getStudent().getName(),

                "courseTitle",
                certificate.getCourse().getTitle(),

                "issuedDate",
                certificate.getIssuedDate()
        );
    }

    public byte[] generateCertificatePdf(Long courseId) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Student Not Found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Course Not Found"));

        Certificate certificate =
                certificateRepository
                        .findByStudentIdAndCourseId(
                                student.getId(),
                                courseId
                        )
                        .orElseThrow(() ->
                                new RuntimeException("Certificate not found"));

        ByteArrayOutputStream out =
                new ByteArrayOutputStream();

        Document document =
                new Document(
                        PageSize.A4.rotate(),
                        45,
                        45,
                        35,
                        35
                );

        try {

            PdfWriter writer =
                    PdfWriter.getInstance(document, out);

            document.open();

            // =========================================
            // BORDER
            // =========================================

            PdfContentByte canvas =
                    writer.getDirectContent();

            Rectangle rect =
                    new Rectangle(
                            20,
                            20,
                            PageSize.A4.rotate().getWidth() - 20,
                            PageSize.A4.rotate().getHeight() - 20
                    );

            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(3);
            rect.setBorderColor(
                    new Color(0, 102, 204)
            );

            canvas.rectangle(rect);


            // =========================================
            // FONTS
            // =========================================

            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            30,
                            new Color(0, 102, 204)
                    );

            Font headingFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            22
                    );

            Font normalFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA,
                            15
                    );

            Font nameFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            28,
                            new Color(34, 139, 34)
                    );


            // =========================================
            // TITLE
            // =========================================

            Paragraph title =
                    new Paragraph(
                            "LearnHub",
                            titleFont
                    );

            title.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(title);


            // Small spacing
            document.add(
                    new Paragraph(" ")
            );


            // =========================================
            // CERTIFICATE HEADING
            // =========================================

            Paragraph heading =
                    new Paragraph(
                            "CERTIFICATE OF COMPLETION",
                            headingFont
                    );

            heading.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(heading);


            // =========================================
            // PRESENTED TO
            // =========================================

            Paragraph presented =
                    new Paragraph(
                            "This certificate is proudly presented to",
                            normalFont
                    );

            presented.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(presented);


            // =========================================
            // STUDENT NAME
            // =========================================

            Paragraph studentName =
                    new Paragraph(
                            student.getName(),
                            nameFont
                    );

            studentName.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(studentName);


            // =========================================
            // COURSE DESCRIPTION
            // =========================================

            Paragraph completion =
                    new Paragraph(
                            "For successfully completing the course",
                            normalFont
                    );

            completion.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(completion);


            // =========================================
            // COURSE NAME
            // =========================================

            Paragraph courseName =
                    new Paragraph(
                            course.getTitle(),
                            headingFont
                    );

            courseName.setAlignment(
                    Element.ALIGN_CENTER
            );

            document.add(courseName);


            // =========================================
            // SPACE BEFORE BOTTOM SECTION
            // =========================================

            document.add(
                    new Paragraph(" ")
            );


            // =========================================
            // QR CODE
            // =========================================

            String verifyUrl =
                    "http://localhost:4200/verify?cert="
                            + certificate.getCertificateNumber();

            byte[] qrBytes =
                    QrCodeUtil.generateQrCode(
                            verifyUrl,
                            150,
                            150
                    );

            Image qrImage =
                    Image.getInstance(qrBytes);

            qrImage.scaleAbsolute(
                    120,
                    120
            );


            // =========================================
            // CERTIFICATE INFO + QR
            // =========================================

            PdfPTable verificationTable =
                    new PdfPTable(2);

            verificationTable.setWidthPercentage(100);

            verificationTable.setWidths(
                    new float[]{75, 25}
            );
            // LEFT
            PdfPCell information =
                    new PdfPCell();

            information.setBorder(
                    Rectangle.NO_BORDER
            );

            information.setVerticalAlignment(
                    Element.ALIGN_MIDDLE
            );

            Paragraph certificateNumber =
                    new Paragraph(
                            "Certificate Number : "
                                    + certificate.getCertificateNumber(),
                            normalFont
                    );

            Paragraph issuedDate =
                    new Paragraph(
                            "Issued Date : "
                                    + certificate.getIssuedDate(),
                            normalFont
                    );

            Paragraph verifyText =
                    new Paragraph(
                            "Scan the QR code to verify this certificate.",
                            normalFont
                    );

            information.addElement(
                    certificateNumber
            );

            information.addElement(
                    issuedDate
            );

            information.addElement(
                    verifyText
            );


            // RIGHT
            PdfPCell qrCell =
                    new PdfPCell();

            qrCell.setBorder(
                    Rectangle.NO_BORDER
            );

            qrCell.setHorizontalAlignment(
                    Element.ALIGN_RIGHT
            );

            qrCell.setVerticalAlignment(
                    Element.ALIGN_MIDDLE
            );

            qrCell.addElement(
                    qrImage
            );


            verificationTable.addCell(
                    information
            );

            verificationTable.addCell(
                    qrCell
            );

            document.add(
                    verificationTable
            );


            // =========================================
            // SIGNATURES
            // =========================================

            PdfPTable signatureTable =
                    new PdfPTable(2);

            signatureTable.setWidthPercentage(100);

            PdfPCell instructor =
                    new PdfPCell(
                            new Phrase("Instructor")
                    );

            instructor.setBorder(
                    Rectangle.NO_BORDER
            );

            PdfPCell admin =
                    new PdfPCell(
                            new Phrase(
                                    "LearnHub Administrator"
                            )
                    );

            admin.setBorder(
                    Rectangle.NO_BORDER
            );

            admin.setHorizontalAlignment(
                    Element.ALIGN_RIGHT
            );

            signatureTable.addCell(
                    instructor
            );

            signatureTable.addCell(
                    admin
            );

            document.add(
                    signatureTable
            );


            // =========================================
            // CLOSE
            // =========================================

            document.close();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error generating PDF",
                    e
            );
        }

        return out.toByteArray();
    }    public List<CertificateDTO> generateCertificatesByStudent() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException("Student Not Found"));

        List<Certificate> certificates =
                certificateRepository.findByStudentId(student.getId());

        return certificates.stream()
                .map(CertificateDTOMapper::mapToCertificateDTO)
                .collect(Collectors.toList());
    }
}