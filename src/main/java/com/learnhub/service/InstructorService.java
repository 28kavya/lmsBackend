package com.learnhub.service;

import com.learnhub.dto.CourseDTO;
import com.learnhub.dto.InstructorStudentDTO;
import com.learnhub.dto.mapper.CourseDTOMapper;
import com.learnhub.entity.User;
import com.learnhub.repository.CourseRepository;
import com.learnhub.repository.EnrollmentRepository;
import com.learnhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InstructorService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;


    @Autowired
    private EnrollmentRepository enrollmentRepository;

    public List<CourseDTO> getInstructorCourses(String email){

        User instructor = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Instructor not found"));

        return courseRepository.findByInstructor(instructor)
                .stream()
                .map(CourseDTOMapper::mapToCourseDTO)
                .toList();
    }

    public List<InstructorStudentDTO> getStudents(String email){

        User instructor = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Instructor not found"));

        return enrollmentRepository.findByCourseInstructor(instructor)
                .stream()
                .map(enrollment -> InstructorStudentDTO.builder()
                        .id(enrollment.getStudent().getId())
                        .name(enrollment.getStudent().getName())
                        .email(enrollment.getStudent().getEmail())
                        .course(enrollment.getCourse().getTitle())
                        .status(enrollment.getStatus())
                        .build())
                .toList();
    }

}
