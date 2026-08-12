package com.example.com.e_com.logintelligence.dto;

import java.util.List;

public class LogAnalysisResponse {

    private int totalLogsAnalyzed;
    private int incidentsDetected;
    private List<IncidentResponse> incidents;

    public int getTotalLogsAnalyzed() {
        return totalLogsAnalyzed;
    }

    public void setTotalLogsAnalyzed(int totalLogsAnalyzed) {
        this.totalLogsAnalyzed = totalLogsAnalyzed;
    }

    public int getIncidentsDetected() {
        return incidentsDetected;
    }

    public void setIncidentsDetected(int incidentsDetected) {
        this.incidentsDetected = incidentsDetected;
    }

    public List<IncidentResponse> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<IncidentResponse> incidents) {
        this.incidents = incidents;
    }
}
