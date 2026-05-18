package com.ramiro.financeapi.controller;

import com.ramiro.financeapi.dto.AnalyticsResponse;
import com.ramiro.financeapi.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping
    public AnalyticsResponse getSummary() {
        return analyticsService.getSummary();
    }
}
