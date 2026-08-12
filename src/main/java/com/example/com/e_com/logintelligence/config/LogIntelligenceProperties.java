package com.example.com.e_com.logintelligence.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "logintelligence")
public class LogIntelligenceProperties {

    private String logFile = "logs/ecommerce-application.json";
    private String serviceName = "ecommerce-backend";
    private int performanceThresholdMs = 1000;
    private int incidentWindowMinutes = 5;
    private int minIncidentOccurrences = 5;
    private int baselineWindowMinutes = 30;
    private int errorSpikeMultiplier = 3;

    public String getLogFile() {
        return logFile;
    }

    public void setLogFile(String logFile) {
        this.logFile = logFile;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getPerformanceThresholdMs() {
        return performanceThresholdMs;
    }

    public void setPerformanceThresholdMs(int performanceThresholdMs) {
        this.performanceThresholdMs = performanceThresholdMs;
    }

    public int getIncidentWindowMinutes() {
        return incidentWindowMinutes;
    }

    public void setIncidentWindowMinutes(int incidentWindowMinutes) {
        this.incidentWindowMinutes = incidentWindowMinutes;
    }

    public int getMinIncidentOccurrences() {
        return minIncidentOccurrences;
    }

    public void setMinIncidentOccurrences(int minIncidentOccurrences) {
        this.minIncidentOccurrences = minIncidentOccurrences;
    }

    public int getBaselineWindowMinutes() {
        return baselineWindowMinutes;
    }

    public void setBaselineWindowMinutes(int baselineWindowMinutes) {
        this.baselineWindowMinutes = baselineWindowMinutes;
    }

    public int getErrorSpikeMultiplier() {
        return errorSpikeMultiplier;
    }

    public void setErrorSpikeMultiplier(int errorSpikeMultiplier) {
        this.errorSpikeMultiplier = errorSpikeMultiplier;
    }
}
