package com.learnhub.controller;

import com.learnhub.dto.CourseDTO;
import com.learnhub.entity.Course;
import com.learnhub.response.ResponseApi;
import com.learnhub.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping("addcourse")
    public ResponseEntity<ResponseApi>  addCourse(@RequestBody Course course) {
        CourseDTO courseDTO=courseService.addCourse(course);
        return new ResponseEntity<>(ResponseApi.builder()
                .message("Course Created")
                .data(courseDTO).build(), HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/getcourse/{id}")
    public CourseDTO getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PutMapping("/updatecourse/{id}")
    public Course updateCourse(@PathVariable Long id, @RequestBody Course course) {
        return courseService.updateCourse(id, course);
    }

    @DeleteMapping("/deletecourse/{id}")
    public String deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }
}
