package com.learnhub.controller;

import com.learnhub.dto.EnrollmentDTO;
import com.learnhub.entity.Enrollment;
import com.learnhub.service.EnrollmentService;
import com.learnhub.service.JWTFilterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enroll")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;
    @Autowired
    private JWTFilterService jwtFilterService;
//1//1
@PostMapping("/{courseId}")
public EnrollmentDTO enrollStudent(@PathVariable Long courseId, HttpServletRequest request) {

    String authHeader = request.getHeader("Authorization");
    String token = authHeader.substring(7); // Remove "Bearer "

    Long userId = jwtFilterService.extractUserId(token);

    return enrollmentService.enrollStudent(userId, courseId);
}

    @GetMapping("/GetAllEnroll")
    public List<Enrollment> getAllEnrollments() {

        return enrollmentService.getAllEnrollments();
    }
}
