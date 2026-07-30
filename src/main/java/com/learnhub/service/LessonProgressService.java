package com.learnhub.service;

import com.learnhub.entity.*;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.exception.UserNotFoundException;
import com.learnhub.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonProgressService {

    private final LessonRepository lessonRepository;
    private final LessonProgressRepository lessonProgressRepository;
    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;

    private User getLoggedInUser(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .orElseThrow(()->
                        new UserNotFoundException("User Not Found"));

    }

    public void completeVideo(Long lessonId){

        User user = getLoggedInUser();

        Lesson lesson = lessonRepository.findById(lessonId)
                        .orElseThrow(()->
                                new ResourceNotFoundException("Lesson Not Found"));

        LessonProgress progress =
                lessonProgressRepository
                        .findByUserIdAndLessonId(user.getId(),lessonId)
                        .orElse(
                                LessonProgress.builder()
                                        .lesson(lesson)
                                        .user(user)
                                        .build()
                        );

        progress.setVideoCompleted(true);

        lessonProgressRepository.save(progress);

    }

    public void updateQuizStatus(Long lessonId,boolean passed){

        User user = getLoggedInUser();

        System.out.println("USER ID "+user.getId());

        LessonProgress progress =
                lessonProgressRepository
                        .findByUserIdAndLessonId(user.getId(),lessonId)
                        .orElseThrow(()->
                                new RuntimeException("Video not completed"));

        progress.setQuizPassed(passed);
        lessonProgressRepository.save(progress);
        if (passed) {

            Long courseId = progress.getLesson().getCourse().getId();

            long totalLessons = lessonRepository.countByCourseId(courseId);

            long completedLessons =
                    lessonProgressRepository
                            .countByUserIdAndLessonCourseIdAndVideoCompletedTrueAndQuizPassedTrue(
                                    user.getId(),
                                    courseId);

            if (totalLessons == completedLessons) {

                boolean exists =
                        certificateRepository.existsByStudentIdAndCourseId(
                                user.getId(),
                                courseId);

                if (!exists) {

                    Certificate certificate = new Certificate();

                    certificate.setStudent(user);

                    certificate.setCourse(progress.getLesson().getCourse());

                    certificate.setIssuedDate(LocalDate.now());

                    certificate.setCertificateNumber(
                            UUID.randomUUID().toString());

                    certificateRepository.save(certificate);
                }
            }
        }

    }

    public boolean isLessonUnlocked(Long lessonId){

        User user = getLoggedInUser();

        Lesson lesson =
                lessonRepository.findById(lessonId)
                        .orElseThrow(()->
                                new ResourceNotFoundException("Lesson Not Found"));

        if(lesson.getLessonOrder()==1){

            return true;

        }

        Lesson previousLesson =
                lessonRepository
                        .findByCourseIdAndLessonOrder(
                                lesson.getCourse().getId(),
                                lesson.getLessonOrder()-1)
                        .orElseThrow(()->
                                new RuntimeException("Previous lesson not found"));

        LessonProgress progress =
                lessonProgressRepository
                        .findByUserIdAndLessonId(
                                user.getId(),
                                previousLesson.getId())
                        .orElse(null);

        return progress != null
                &&
                progress.isVideoCompleted()
                &&
                progress.isQuizPassed();

    }

}