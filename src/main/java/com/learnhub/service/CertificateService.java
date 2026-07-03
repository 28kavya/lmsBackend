package com.learnhub.service;

import com.learnhub.dto.CertificateDTO;
import com.learnhub.dto.mapper.CertificateDTOMapper;
import com.learnhub.entity.*;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.exception.UserNotFoundException;
import com.learnhub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ProgressRepository progressRepository;
    private final CertificateRepository certificateRepository;

    public CertificateDTO generateCertificate(Long courseId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Student Not Found"));
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));

        Progress progress = progressRepository.findByStudentIdAndCourseId(student.getId(), courseId)
                        .orElseThrow(() -> new RuntimeException("Progress Not Found"));

        if(!progress.getStatus().equalsIgnoreCase("COMPLETED")){

            throw new ResourceNotFoundException("Course Not Completed Yet");
        }
        if(certificateRepository
                .findByStudentIdAndCourseId(student.getId(), courseId)
                .isPresent()){

            throw new RuntimeException("Certificate Already Generated");
        }
        Certificate certificate = new Certificate();

        certificate.setStudent(student);
        certificate.setCourse(course);
        certificate.setIssuedDate(LocalDate.now());

        certificate.setCertificateNumber("CERT-" + UUID.randomUUID().toString().substring(0,8).toUpperCase());

        Certificate saved = certificateRepository.save(certificate);

        return CertificateDTOMapper.mapToCertificateDTO(saved);
    }
    public List<CertificateDTO> getCertificatesByStudent(){
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        User student = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("Student Not Found"));


        return certificateRepository.findByStudentId(student.getId())
                .stream()
                .map(CertificateDTOMapper::mapToCertificateDTO)
                .collect(Collectors.toList());
    }
}
