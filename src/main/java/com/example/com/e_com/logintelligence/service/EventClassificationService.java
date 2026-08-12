package com.example.com.e_com.logintelligence.service;

import com.example.com.e_com.logintelligence.model.IncidentCategory;
import com.example.com.e_com.logintelligence.model.LogEvent;
import org.springframework.stereotype.Service;

@Service
public class EventClassificationService {

    public IncidentCategory classify(LogEvent event) {
        String eventName = safe(event.getEvent());
        String message = safe(event.getMessage()).toLowerCase();
        String status = safe(event.getStatus());

        if (eventName.contains("AUTHENTICATION") || message.contains("authentication")) {
            return IncidentCategory.AUTHENTICATION;
        }
        if (eventName.contains("PAYMENT") || message.contains("payment")) {
            return IncidentCategory.PAYMENT;
        }
        if (eventName.contains("INVENTORY") || message.contains("inventory")) {
            return IncidentCategory.INVENTORY;
        }
        if (eventName.contains("SLOW_REQUEST") || message.contains("durationms") || message.contains("slow")) {
            return IncidentCategory.PERFORMANCE;
        }
        if (eventName.contains("UNAUTHORIZED") || eventName.contains("FORBIDDEN") || status.equals("401") || status.equals("403")
                || message.contains("access denied") || message.contains("forbidden")) {
            return IncidentCategory.SECURITY;
        }
        if (eventName.contains("DATABASE") || message.contains("connection timeout") || message.contains("sql")
                || message.contains("database") || message.contains("connection refused")) {
            return IncidentCategory.DATABASE;
        }
        if (eventName.contains("ORDER_FAILED") || eventName.contains("ORDER_CREATED") || message.contains("order")) {
            return IncidentCategory.PAYMENT;
        }
        if (message.contains("exception") || event.getLevel() != null && event.getLevel().equalsIgnoreCase("ERROR")) {
            return IncidentCategory.GENERAL_APPLICATION;
        }
        return IncidentCategory.GENERAL_APPLICATION;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
