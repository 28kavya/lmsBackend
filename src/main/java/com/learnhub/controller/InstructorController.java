package com.learnhub.controller;

import com.learnhub.dto.CourseDTO;
import com.learnhub.dto.InstructorStudentDTO;
import com.learnhub.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/instructor")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @GetMapping("/my-courses")
    public List<CourseDTO> getMyCourses(Authentication authentication){

        String email = authentication.getName();

        return instructorService.getInstructorCourses(email);

    }

    @GetMapping("/students")
    public List<InstructorStudentDTO> getStudents(Authentication authentication) {

        return instructorService.getStudents(authentication.getName());
    }
    @DeleteMapping("/delete/{id}")
    public String deleteInstructor(@PathVariable Long id) {

        return instructorService.deleteInstructor(id);

    }
}