package com.learnhub.service;

import com.learnhub.dto.CourseDTO;
import com.learnhub.dto.mapper.CourseDTOMapper;
import com.learnhub.entity.Course;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public CourseDTO addCourse(Course course) {
        Course course1= courseRepository.save(course);
        return CourseDTOMapper.mapToCourseDTO(course1);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public CourseDTO getCourseById(Long id) {
        Course course2=courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));
        return CourseDTOMapper.mapToCourseDTO(course2);
    }

    public Course updateCourse(Long id, Course updatedCourse) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));

        course.setTitle(updatedCourse.getTitle());
        course.setDescription(updatedCourse.getDescription());
        course.setPrice(updatedCourse.getPrice());
        course.setInstructor(updatedCourse.getInstructor());

        return courseRepository.save(course);
    }

    public String deleteCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));

        courseRepository.delete(course);

        return "Course Deleted Successfully";
    }
}