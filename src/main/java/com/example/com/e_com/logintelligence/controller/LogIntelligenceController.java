package com.example.com.e_com.logintelligence.controller;

import com.example.com.e_com.logintelligence.dto.LogAnalysisResponse;
import com.example.com.e_com.logintelligence.dto.LogSummaryResponse;
import com.example.com.e_com.logintelligence.service.LogAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/log-intelligence")
@RequiredArgsConstructor
public class LogIntelligenceController {

    private final LogAnalysisService logAnalysisService;

    @GetMapping("/analyze")
    public LogAnalysisResponse analyze() {
        return logAnalysisService.analyze();
    }

    @GetMapping("/summary")
    public LogSummaryResponse summary() {
        return logAnalysisService.summary();
    }
}
