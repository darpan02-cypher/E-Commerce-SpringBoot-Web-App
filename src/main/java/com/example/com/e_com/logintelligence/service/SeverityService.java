package com.example.com.e_com.logintelligence.service;

import com.example.com.e_com.logintelligence.model.IncidentCategory;
import com.example.com.e_com.logintelligence.model.LogEvent;
import com.example.com.e_com.logintelligence.model.LogSeverity;
import org.springframework.stereotype.Service;

@Service
public class SeverityService {

    public LogSeverity determineSeverity(LogEvent event, int occurrenceCount) {
        IncidentCategory category = event.getCategory();
        String level = event.getLevel();

        if (category == IncidentCategory.SECURITY && occurrenceCount >= 3) {
            return LogSeverity.CRITICAL;
        }
        if (category == IncidentCategory.AUTHENTICATION && occurrenceCount >= 5) {
            return LogSeverity.HIGH;
        }
        if (occurrenceCount >= 5 && "ERROR".equalsIgnoreCase(level)) {
            return LogSeverity.HIGH;
        }
        if ("ERROR".equalsIgnoreCase(level)) {
            return LogSeverity.MEDIUM;
        }
        if ("WARN".equalsIgnoreCase(level)) {
            return LogSeverity.MEDIUM;
        }
        return LogSeverity.LOW;
    }
}
