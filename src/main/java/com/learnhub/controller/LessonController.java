package com.learnhub.controller;

import com.learnhub.dto.LessonDTO;
import com.learnhub.entity.Lesson;
import com.learnhub.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("api/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping("/addlesson/{courseId}")
    public LessonDTO addLesson(@PathVariable Long courseId, @RequestBody Lesson lesson) {
        return lessonService.addLesson(courseId, lesson);
    }

    @GetMapping("/getlesson/{courseId}")
    public List<LessonDTO> getLessons(@PathVariable Long courseId) {
        return lessonService.getLessonsByCourse(courseId);
    }
}