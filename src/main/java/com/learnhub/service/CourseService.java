package com.learnhub.service;

import com.learnhub.dto.CourseAdminDTO;
import com.learnhub.dto.CourseDTO;
import com.learnhub.dto.mapper.CourseAdminDTOMapper;
import com.learnhub.dto.mapper.CourseDTOMapper;
import com.learnhub.entity.Course;
import com.learnhub.entity.Instructor;
import com.learnhub.entity.User;
import com.learnhub.exception.ResourceNotFoundException;
import com.learnhub.repository.CourseRepository;
import com.learnhub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;
    public Course addCourse(CourseAdminDTO dto) {

        User instructor = userRepository.findById(dto.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor not found"));

        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setPrice(dto.getPrice());
        course.setInstructor(instructor);

        return courseRepository.save(course);
    }

    public List<CourseDTO> getAllCoursesForStudent() {

        return courseRepository.findAll()
                .stream()
                .map(CourseDTOMapper::mapToCourseDTO)
                .toList();
    }

    public List<CourseAdminDTO> getAllCoursesForAdmin() {

        List<Course> courses = courseRepository.findAll();

        for (Course c : courses) {
            System.out.println("Course: " + c.getTitle());
            System.out.println("Instructor: " + c.getInstructor());
            if (c.getInstructor() != null) {
                System.out.println("Instructor Name: " + c.getInstructor().getName());
            }
        }

        return courses.stream()
                .map(CourseAdminDTOMapper::mapToCourseAdminDTO)
                .toList();
    }

    public CourseAdminDTO getCourseById(Long id) {
        Course course2=courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));
        return CourseAdminDTOMapper.mapToCourseAdminDTO(course2);
    }

    public CourseAdminDTO updateCourse(Long id, CourseDTO updatedCourse) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setTitle(updatedCourse.getTitle());
        course.setDescription(updatedCourse.getDescription());
        course.setPrice(updatedCourse.getPrice());


        User exeUser = userRepository.findByName(updatedCourse.getInstructor()).get();
        course.setInstructor(exeUser);
        // if you have these fields
        // course.setCategory(updatedCourse.getCategory());
        // course.setDuration(updatedCourse.getDuration());
        // course.setImage(updatedCourse.getImage());

         Course course1=  courseRepository.save(course);
        return CourseAdminDTOMapper.mapToCourseAdminDTO(course1);
    }


    public String deleteCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found"));

        courseRepository.delete(course);

        return "Course Deleted Successfully";
    }
}