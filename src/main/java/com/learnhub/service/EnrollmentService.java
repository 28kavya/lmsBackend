package com.learnhub.service;

import com.learnhub.dto.EnrollmentDTO;
import com.learnhub.dto.mapper.EnrollmentDTOMapper;
import com.learnhub.entity.Course;
import com.learnhub.entity.Enrollment;
import com.learnhub.entity.User;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.exception.UserNotFoundException;
import com.learnhub.repository.CourseRepository;
import com.learnhub.repository.EnrollmentRepository;
import com.learnhub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class EnrollmentService {
    @Autowired
    private EnrollmentRepository enrollmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CourseRepository courseRepository;

    public EnrollmentDTO enrollStudent(Long userId, Long courseId) {

        if (enrollmentRepository.existsByStudentIdAndCourseId(userId, courseId)) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        User student = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("Student Not Found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));

        Enrollment enrollment = new Enrollment();

        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setStatus("ENROLLED");
        Enrollment enrollment1 = enrollmentRepository.save(enrollment);

        System.out.println("Enrollment ID : " + enrollment.getId());
        System.out.println("Student : " + enrollment.getStudent());
        System.out.println("Course : " + enrollment.getCourse());

        return EnrollmentDTOMapper.mapToEnrollmentDTO(enrollment1);
    }

    public List<Enrollment> getAllEnrollments() {

        return enrollmentRepository.findAll();
    }
}
