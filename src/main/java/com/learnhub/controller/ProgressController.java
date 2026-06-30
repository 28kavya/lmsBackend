package com.learnhub.controller;

import com.learnhub.dto.ProgressResponseDTO;
import com.learnhub.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
@RequiredArgsConstructor
public class ProgressController {

    private final ProgressService progressService;

    @PostMapping("/{studentId}/{courseId}")
    public ProgressResponseDTO updateProgress(@PathVariable Long studentId, @PathVariable Long courseId){

        return progressService.updateProgress(studentId, courseId);
    }

    @GetMapping("/{studentId}/{courseId}")
    public ProgressResponseDTO getProgress(@PathVariable Long studentId, @PathVariable Long courseId){

        return progressService.getProgress(studentId, courseId);
    }
}