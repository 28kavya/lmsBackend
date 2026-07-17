package com.learnhub.controller;

import com.learnhub.dto.CourseAdminDTO;
import com.learnhub.dto.CourseDTO;
import com.learnhub.dto.InstructorDTO;
import com.learnhub.dto.StudentDTO;
import com.learnhub.dto.mapper.InstructorDTOMapper;
import com.learnhub.dto.mapper.StudentDtoMapper;
import com.learnhub.entity.Course;
import com.learnhub.entity.Roles;
import com.learnhub.entity.User;
import com.learnhub.repository.UserRepository;
import com.learnhub.response.ResponseApi;
import com.learnhub.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/courses")
    public ResponseEntity<Course> addCourse(@RequestBody CourseAdminDTO dto){
        return ResponseEntity.ok(courseService.addCourse(dto));
    }
//    @GetMapping("/instructors")
//    public List<User> getAllInstructors(){
//
//        return userRepository.findByRole(Roles.INSTRUCTOR);
//
//    }
@GetMapping("/instructors")
public List<InstructorDTO> getAllInstructors() {

    return userRepository.findByRole(Roles.INSTRUCTOR)
            .stream()
            .map(InstructorDTOMapper::map)
            .toList();
}
    @GetMapping("/students")
    public List<User> getAllStudents(){

        return userRepository.findByRole(Roles.STUDENT);

    }
    //enrollment details
    @GetMapping("/studentsenrollments")
    public List<StudentDTO> getStudents() {

        return userRepository.findByRole(Roles.STUDENT)
                .stream()
                .map(StudentDtoMapper::map)
                .toList();
    }



    @GetMapping("/getall")
    public Object getAllCourses(Authentication authentication) {

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN"));

        if (isAdmin) {
            return courseService.getAllCoursesForAdmin();
        }

        return courseService.getAllCoursesForStudent();
    }
    @GetMapping("/getcourse/{id}")
    public ResponseEntity<CourseAdminDTO> getCourseById(@PathVariable Long id) {
        CourseAdminDTO course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }
    @PutMapping("/updatecourse/{id}")
    public ResponseEntity<CourseAdminDTO> updateCourse(
            @PathVariable Long id,
            @RequestBody CourseDTO updatedCourse) {

        CourseAdminDTO course = courseService.updateCourse(id, updatedCourse);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping("/deletecourse/{id}")
    public String deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }
}
