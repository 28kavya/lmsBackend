package com.learnhub.service;

import com.learnhub.dto.ProgressResponseDTO;
import com.learnhub.entity.*;
import com.learnhub.dto.mapper.ProgressDTOMapper;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.exception.UserNotFoundException;
import com.learnhub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProgressService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final QuizResultRepository quizResultRepository;
    private final ProgressRepository progressRepository;

    public ProgressResponseDTO updateProgress(Long studentId, Long courseId){

        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new UserNotFoundException("Student Not Found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));

        QuizResult quizResult = quizResultRepository.findTopByStudentIdOrderByPercentageDesc(studentId)
                        .orElseThrow(() -> new RuntimeException("Quiz Result Not Found"));

        Progress progress = progressRepository.findByStudentIdAndCourseId(studentId, courseId).orElse(new Progress());

        progress.setStudent(student);
        progress.setCourse(course);

        if(quizResult.getPercentage() >= 70){
            progress.setPercentage(100);
            progress.setStatus("COMPLETED");

        }
        else{
            progress.setPercentage(quizResult.getPercentage());
            progress.setStatus("IN_PROGRESS");
        }
        Progress saved = progressRepository.save(progress);

        return ProgressDTOMapper.mapToProgressResponseDTO(saved);
    }

    public ProgressResponseDTO getProgress(Long studentId, Long courseId){

        Progress progress = progressRepository.findByStudentIdAndCourseId(studentId, courseId).orElseThrow(() -> new RuntimeException("Progress Not Found"));

        return ProgressDTOMapper.mapToProgressResponseDTO(progress);
    }
}