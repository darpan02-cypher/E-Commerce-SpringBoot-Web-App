package com.example.com.e_com.logintelligence.dto;

import java.util.Map;

public class LogSummaryResponse {

    private int logsAnalyzed;
    private int errors;
    private int warnings;
    private int incidents;
    private Map<String, Integer> categories;

    public int getLogsAnalyzed() {
        return logsAnalyzed;
    }

    public void setLogsAnalyzed(int logsAnalyzed) {
        this.logsAnalyzed = logsAnalyzed;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public int getWarnings() {
        return warnings;
    }

    public void setWarnings(int warnings) {
        this.warnings = warnings;
    }

    public int getIncidents() {
        return incidents;
    }

    public void setIncidents(int incidents) {
        this.incidents = incidents;
    }

    public Map<String, Integer> getCategories() {
        return categories;
    }

    public void setCategories(Map<String, Integer> categories) {
        this.categories = categories;
    }
}
