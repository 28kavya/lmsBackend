package com.learnhub.service;

import com.learnhub.dto.CertificateDTO;
import com.learnhub.dto.mapper.CertificateDTOMapper;
import java.util.List;

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
                        .findByStudentIdAndCourseId(student.getId(), courseId)
                        .orElseThrow(() ->
                                new RuntimeException("Certificate not found"));
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate());

        try {

            PdfWriter writer = PdfWriter.getInstance(document, out);
            document.open();
            // ===== Border =====
            PdfContentByte canvas = writer.getDirectContent();
            Rectangle rect = new Rectangle(20, 20, PageSize.A4.rotate().getWidth() - 20,
                    PageSize.A4.rotate().getHeight() - 20);

            rect.setBorder(Rectangle.BOX);
            rect.setBorderWidth(3);
            rect.setBorderColor(new Color(0, 102, 204));

            canvas.rectangle(rect);
            // ===== Fonts =====
            Font titleFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    30,
                    new Color(0,102,204));
            Font headingFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    22);
            Font normalFont = FontFactory.getFont(
                    FontFactory.HELVETICA,
                    16);
            Font nameFont = FontFactory.getFont(
                    FontFactory.HELVETICA_BOLD,
                    28,
                    new Color(34,139,34));
            // ===== LearnHub =====
            Paragraph title = new Paragraph("LearnHub", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));

            // ===== Certificate =====
            Paragraph heading =
                    new Paragraph(
                            "CERTIFICATE OF COMPLETION",
                            headingFont);
            heading.setAlignment(Element.ALIGN_CENTER);
            document.add(heading);
            document.add(new Paragraph("\n\n"));

            Paragraph line1 =
                    new Paragraph("This certificate is proudly presented to", normalFont);

            line1.setAlignment(Element.ALIGN_CENTER);
            document.add(line1);
            document.add(new Paragraph("\n"));
            Paragraph studentName =
                    new Paragraph(student.getName(), nameFont);

            studentName.setAlignment(Element.ALIGN_CENTER);
            document.add(studentName);
            document.add(new Paragraph("\n"));
            Paragraph line2 =
                    new Paragraph("For successfully completing the course", normalFont);

            line2.setAlignment(Element.ALIGN_CENTER);
            document.add(line2);
            document.add(new Paragraph("\n"));
            Paragraph courseName =
                    new Paragraph(course.getTitle(), headingFont);
            courseName.setAlignment(Element.ALIGN_CENTER);
            document.add(courseName);
            document.add(new Paragraph("\n\n\n"));
            document.add(new Paragraph("Certificate Number : " + certificate.getCertificateNumber(), normalFont));
            document.add(new Paragraph("Issued Date : " + certificate.getIssuedDate(), normalFont));

            document.add(new Paragraph("\n\n\n"));
            PdfPTable table = new PdfPTable(2);

            table.setWidthPercentage(100);
            PdfPCell left = new PdfPCell(
                    new Phrase("Instructor"));

            left.setBorder(Rectangle.NO_BORDER);
            PdfPCell right = new PdfPCell(
                    new Phrase("LearnHub Administrator"));

            right.setHorizontalAlignment(Element.ALIGN_RIGHT);
            right.setBorder(Rectangle.NO_BORDER);

            table.addCell(left);
            table.addCell(right);
            document.add(table);

            document.close();

        } catch (Exception e) {
            throw new RuntimeException("Error generating PDF", e);
        }
        return out.toByteArray();

    }
    public List<CertificateDTO> generateCertificatesByStudent() {

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