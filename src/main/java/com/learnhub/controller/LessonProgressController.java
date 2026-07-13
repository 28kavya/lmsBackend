package com.learnhub.controller;

import com.learnhub.service.LessonProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
public class LessonProgressController {

    private final LessonProgressService lessonProgressService;

    @PostMapping("/{lessonId}/video-complete")
    public ResponseEntity<String> completeVideo(
            @PathVariable Long lessonId){

        lessonProgressService.completeVideo(lessonId);

        return ResponseEntity.ok("Video Completed");

    }

    @GetMapping("/{lessonId}/unlock")
    public ResponseEntity<Boolean> unlockLesson(
            @PathVariable Long lessonId){

        return ResponseEntity.ok(
                lessonProgressService.isLessonUnlocked(lessonId)
        );

    }

}