package com.example.com.e_com.logintelligence.service;

import com.example.com.e_com.logintelligence.config.LogIntelligenceProperties;
import com.example.com.e_com.logintelligence.model.Incident;
import com.example.com.e_com.logintelligence.model.IncidentCategory;
import com.example.com.e_com.logintelligence.model.LogEvent;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class IncidentDetectionService {

    private final LogIntelligenceProperties properties;
    private final SeverityService severityService;

    public IncidentDetectionService(LogIntelligenceProperties properties, SeverityService severityService) {
        this.properties = properties;
        this.severityService = severityService;
    }

    public List<Incident> detectIncidents(List<LogEvent> events) {
        Map<String, List<LogEvent>> groups = new LinkedHashMap<>();
        for (LogEvent event : events) {
            if (event.getCategory() == null) {
                continue;
            }
            String groupKey = groupKey(event);
            groups.computeIfAbsent(groupKey, key -> new ArrayList<>()).add(event);
        }

        List<Incident> incidents = new ArrayList<>();
        for (List<LogEvent> groupEvents : groups.values()) {
            if (!qualifiesAsIncident(groupEvents)) {
                continue;
            }
            incidents.add(buildIncident(groupEvents));
        }
        return incidents;
    }

    private boolean qualifiesAsIncident(List<LogEvent> events) {
        if (events.isEmpty()) {
            return false;
        }
        IncidentCategory category = events.get(0).getCategory();
        int count = events.size();
        if (category == IncidentCategory.SECURITY && count >= 3) {
            return true;
        }
        return count >= properties.getMinIncidentOccurrences();
    }

    private Incident buildIncident(List<LogEvent> events) {
        events.sort(Comparator.comparing(LogEvent::getTimestamp, Comparator.nullsLast(Comparator.naturalOrder())));
        LogEvent representative = events.get(0);
        String representativeError = chooseRepresentativeError(events);
        Set<String> requestIds = events.stream()
                .map(LogEvent::getRequestId)
                .filter(id -> id != null && !id.isBlank())
                .collect(Collectors.toCollection(HashSet::new));

        Incident incident = new Incident();
        incident.setIncidentId(UUID.randomUUID().toString());
        incident.setCategory(representative.getCategory());
        incident.setSeverity(severityService.determineSeverity(representative, events.size()));
        incident.setFirstSeen(events.get(0).getTimestamp());
        incident.setLastSeen(events.get(events.size() - 1).getTimestamp());
        incident.setOccurrenceCount(events.size());
        incident.setAffectedService(representative.getService());
        incident.setAffectedEndpoint(safe(representative.getEndpoint(), "unknown"));
        incident.setRepresentativeError(representativeError);
        incident.setProbableCause(buildProbableCause(representative.getCategory()));
        incident.setRecommendation(buildRecommendation(representative.getCategory()));
        incident.setRequestIds(requestIds.stream().collect(Collectors.toList()));
        return incident;
    }

    private String chooseRepresentativeError(List<LogEvent> events) {
        for (LogEvent event : events) {
            if (event.getMessage() != null && !event.getMessage().isBlank()) {
                return event.getMessage();
            }
            if (event.getExceptionType() != null) {
                return event.getExceptionType();
            }
        }
        return "Repeated " + events.get(0).getCategory() + " events";
    }

    private String buildProbableCause(IncidentCategory category) {
        return switch (category) {
            case DATABASE -> "Database connectivity or query failure";
            case PAYMENT -> "Payment processing failures or gateway issues";
            case AUTHENTICATION -> "Repeated authentication failures or invalid credentials";
            case SECURITY -> "Unauthorized or forbidden access attempts";
            case PERFORMANCE -> "Requests are slower than expected";
            case INVENTORY -> "Inventory or stock validation errors";
            default -> "Application issue requiring investigation";
        };
    }

    private String buildRecommendation(IncidentCategory category) {
        return switch (category) {
            case DATABASE -> "Check database availability, connection pool, and SQL latency.";
            case PAYMENT -> "Review payment gateway and retry logic.";
            case AUTHENTICATION -> "Inspect authentication flow and user credentials.";
            case SECURITY -> "Review access rules, tokens, and unauthorized activity.";
            case PERFORMANCE -> "Review request latency, profiling, and slow endpoints.";
            case INVENTORY -> "Review stock records and inventory constraints.";
            default -> "Review application logs and trace related requests.";
        };
    }

    private String groupKey(LogEvent event) {
        return event.getCategory() + "|" + safe(event.getEvent(), "unknown") + "|" + safe(event.getEndpoint(), "unknown");
    }

    private String safe(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}
