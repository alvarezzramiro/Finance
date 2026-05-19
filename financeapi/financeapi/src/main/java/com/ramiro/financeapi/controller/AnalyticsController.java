package com.ramiro.financeapi.controller;

import com.ramiro.financeapi.dto.AnalyticsResponse;
import com.ramiro.financeapi.dto.BalanceEvolutionResponse;
import com.ramiro.financeapi.dto.CategoryExpenseResponse;
import com.ramiro.financeapi.dto.MonthlySummaryResponse;
import com.ramiro.financeapi.service.AnalyticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/expenses-by-category")
    public List<CategoryExpenseResponse>
    getExpensesByCategory() {
        return analyticsService.getExpenseByCategory();
    }

    @GetMapping("/monthly-summary")
    public List<MonthlySummaryResponse> getMonthlySummary() {
        return analyticsService.getMonthlySummary();
    }

    @GetMapping("/balance-evolution")
    public List<BalanceEvolutionResponse> getBalanceEvolution() {
        return analyticsService.getBalanceEvolution();
    }
}
