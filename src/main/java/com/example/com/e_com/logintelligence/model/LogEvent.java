package com.example.com.e_com.logintelligence.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LogEvent {

    @JsonProperty("timestamp")
    @JsonAlias({"@timestamp"})
    private String timestamp;

    @JsonProperty("level")
    @JsonAlias({"logLevel", "log_level"})
    private String level;

    @JsonProperty("logger_name")
    @JsonAlias({"loggerName"})
    private String loggerName;

    @JsonProperty("thread_name")
    @JsonAlias({"threadName"})
    private String threadName;

    private String message;

    private Map<String, String> mdc;

    private String stackTrace;

    private IncidentCategory category;

    public Instant getTimestamp() {
        try {
            return timestamp != null ? Instant.parse(timestamp) : null;
        } catch (Exception ex) {
            return null;
        }
    }

    public String getRawTimestamp() {
        return timestamp;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLoggerName() {
        return loggerName;
    }

    public void setLoggerName(String loggerName) {
        this.loggerName = loggerName;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getMdc() {
        return mdc == null ? Collections.emptyMap() : mdc;
    }

    public void setMdc(Map<String, String> mdc) {
        this.mdc = mdc;
    }

    public String getRequestId() {
        return getMdc().get("requestId");
    }

    public String getService() {
        return getMdc().getOrDefault("service", "ecommerce-backend");
    }

    public String getEvent() {
        return getMdc().get("event");
    }

    public String getEndpoint() {
        return getMdc().get("endpoint");
    }

    public String getHttpMethod() {
        return getMdc().get("httpMethod");
    }

    public String getStatus() {
        return getMdc().get("status");
    }

    public String getExceptionType() {
        if (stackTrace != null && stackTrace.contains("Exception")) {
            int index = stackTrace.indexOf("Exception");
            return stackTrace.substring(index).split(":", 2)[0];
        }
        return null;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public IncidentCategory getCategory() {
        return category;
    }

    public void setCategory(IncidentCategory category) {
        this.category = category;
    }
}
