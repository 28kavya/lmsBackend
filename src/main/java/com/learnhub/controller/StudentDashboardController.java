package com.learnhub.controller;

import com.learnhub.dto.MyCourseDTO;
import com.learnhub.dto.StudentDashboardDTO;
import com.learnhub.entity.User;
import com.learnhub.repository.UserRepository;
import com.learnhub.service.StudentDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
@CrossOrigin(origins="*")
public class StudentDashboardController {

    private final StudentDashboardService studentDashboardService;
    private final UserRepository userRepository;


    @GetMapping("/dashboard")
    public StudentDashboardDTO dashboard(Authentication authentication) {

        String email = authentication.getName();

        return studentDashboardService.getDashboard(email);
    }
    @GetMapping("/my-courses")
    public ResponseEntity<List<MyCourseDTO>> getMyCourses(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return ResponseEntity.ok(
                studentDashboardService.getMyCourses(user.getId())
        );
    }

}