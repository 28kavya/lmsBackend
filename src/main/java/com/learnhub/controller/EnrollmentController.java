package com.learnhub.controller;

import com.learnhub.dto.EnrollmentDTO;
import com.learnhub.entity.Enrollment;
import com.learnhub.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enroll")
public class EnrollmentController {
    @Autowired
    private EnrollmentService enrollmentService;
//1//1
    @PostMapping("/{userId}/{courseId}")
    public EnrollmentDTO enrollStudent(@PathVariable Long userId, @PathVariable Long courseId) {
        return enrollmentService.enrollStudent(userId, courseId);
    }

    @GetMapping("/GetAllEnroll")
    public List<Enrollment> getAllEnrollments() {

        return enrollmentService.getAllEnrollments();
    }
}
