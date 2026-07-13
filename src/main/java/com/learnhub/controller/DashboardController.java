package com.learnhub.controller;

import com.learnhub.dto.DashboardDTO;
import com.learnhub.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/dashboard")
    public DashboardDTO getDashboard() {

        return dashboardService.getDashboardStats();

    }

}