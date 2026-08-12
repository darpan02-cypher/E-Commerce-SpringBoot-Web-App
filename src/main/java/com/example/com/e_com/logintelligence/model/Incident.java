package com.example.com.e_com.logintelligence.model;

import java.time.Instant;
import java.util.List;

public class Incident {

    private String incidentId;
    private IncidentCategory category;
    private LogSeverity severity;
    private Instant firstSeen;
    private Instant lastSeen;
    private int occurrenceCount;
    private String affectedService;
    private String affectedEndpoint;
    private String representativeError;
    private String probableCause;
    private String recommendation;
    private List<String> requestIds;

    public String getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(String incidentId) {
        this.incidentId = incidentId;
    }

    public IncidentCategory getCategory() {
        return category;
    }

    public void setCategory(IncidentCategory category) {
        this.category = category;
    }

    public LogSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(LogSeverity severity) {
        this.severity = severity;
    }

    public Instant getFirstSeen() {
        return firstSeen;
    }

    public void setFirstSeen(Instant firstSeen) {
        this.firstSeen = firstSeen;
    }

    public Instant getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Instant lastSeen) {
        this.lastSeen = lastSeen;
    }

    public int getOccurrenceCount() {
        return occurrenceCount;
    }

    public void setOccurrenceCount(int occurrenceCount) {
        this.occurrenceCount = occurrenceCount;
    }

    public String getAffectedService() {
        return affectedService;
    }

    public void setAffectedService(String affectedService) {
        this.affectedService = affectedService;
    }

    public String getAffectedEndpoint() {
        return affectedEndpoint;
    }

    public void setAffectedEndpoint(String affectedEndpoint) {
        this.affectedEndpoint = affectedEndpoint;
    }

    public String getRepresentativeError() {
        return representativeError;
    }

    public void setRepresentativeError(String representativeError) {
        this.representativeError = representativeError;
    }

    public String getProbableCause() {
        return probableCause;
    }

    public void setProbableCause(String probableCause) {
        this.probableCause = probableCause;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public List<String> getRequestIds() {
        return requestIds;
    }

    public void setRequestIds(List<String> requestIds) {
        this.requestIds = requestIds;
    }
}
