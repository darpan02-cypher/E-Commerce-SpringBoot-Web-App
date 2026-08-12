package com.example.com.e_com.logintelligence.service;

import com.example.com.e_com.logintelligence.config.LogIntelligenceProperties;
import com.example.com.e_com.logintelligence.dto.IncidentResponse;
import com.example.com.e_com.logintelligence.dto.LogAnalysisResponse;
import com.example.com.e_com.logintelligence.dto.LogSummaryResponse;
import com.example.com.e_com.logintelligence.model.Incident;
import com.example.com.e_com.logintelligence.model.LogEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class LogAnalysisService {

    private final LogIntelligenceProperties properties;
    private final LogParserService parserService;
    private final EventClassificationService classificationService;
    private final IncidentDetectionService incidentDetectionService;

    public LogAnalysisService(LogIntelligenceProperties properties,
                              LogParserService parserService,
                              EventClassificationService classificationService,
                              IncidentDetectionService incidentDetectionService) {
        this.properties = properties;
        this.parserService = parserService;
        this.classificationService = classificationService;
        this.incidentDetectionService = incidentDetectionService;
    }

    public LogAnalysisResponse analyze() {
        List<LogEvent> events = parserService.parseEvents(properties.getLogFile());
        events.forEach(this::applyClassification);
        List<Incident> incidents = incidentDetectionService.detectIncidents(events);

        LogAnalysisResponse response = new LogAnalysisResponse();
        response.setTotalLogsAnalyzed(events.size());
        response.setIncidentsDetected(incidents.size());
        response.setIncidents(incidents.stream().map(this::toDto).collect(Collectors.toList()));
        return response;
    }

    public LogSummaryResponse summary() {
        List<LogEvent> events = parserService.parseEvents(properties.getLogFile());
        events.forEach(this::applyClassification);
        List<Incident> incidents = incidentDetectionService.detectIncidents(events);

        LogSummaryResponse response = new LogSummaryResponse();
        response.setLogsAnalyzed(events.size());
        response.setErrors((int) events.stream().filter(e -> "ERROR".equalsIgnoreCase(e.getLevel())).count());
        response.setWarnings((int) events.stream().filter(e -> "WARN".equalsIgnoreCase(e.getLevel())).count());
        response.setIncidents(incidents.size());
        response.setCategories(events.stream()
                .map(LogEvent::getCategory)
                .filter(cat -> cat != null)
                .collect(Collectors.groupingBy(Enum::name, Collectors.summingInt(e -> 1))));
        return response;
    }

    private void applyClassification(LogEvent event) {
        if (event.getCategory() == null) {
            event.setCategory(classificationService.classify(event));
        }
    }

    private IncidentResponse toDto(Incident incident) {
        IncidentResponse dto = new IncidentResponse();
        dto.setIncidentId(incident.getIncidentId());
        dto.setCategory(incident.getCategory());
        dto.setSeverity(incident.getSeverity());
        dto.setFirstSeen(incident.getFirstSeen());
        dto.setLastSeen(incident.getLastSeen());
        dto.setOccurrenceCount(incident.getOccurrenceCount());
        dto.setAffectedService(incident.getAffectedService());
        dto.setAffectedEndpoint(incident.getAffectedEndpoint());
        dto.setRepresentativeError(incident.getRepresentativeError());
        dto.setProbableCause(incident.getProbableCause());
        dto.setRecommendation(incident.getRecommendation());
        dto.setRequestIds(incident.getRequestIds());
        return dto;
    }
}
