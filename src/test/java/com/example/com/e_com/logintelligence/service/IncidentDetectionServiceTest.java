package com.example.com.e_com.logintelligence.service;

import com.example.com.e_com.logintelligence.config.LogIntelligenceProperties;
import com.example.com.e_com.logintelligence.model.Incident;
import com.example.com.e_com.logintelligence.model.IncidentCategory;
import com.example.com.e_com.logintelligence.model.LogEvent;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IncidentDetectionServiceTest {

    @Test
    void groupsRepeatedPaymentEventsIntoSingleIncident() {
        LogIntelligenceProperties properties = new LogIntelligenceProperties();
        properties.setMinIncidentOccurrences(2);
        properties.setIncidentWindowMinutes(5);

        SeverityService severityService = new SeverityService();
        IncidentDetectionService detectionService = new IncidentDetectionService(properties, severityService);

        LogEvent event1 = new LogEvent();
        event1.setLevel("ERROR");
        event1.setCategory(IncidentCategory.PAYMENT);
        event1.setMessage("PAYMENT_FAILED: gateway timeout");

        LogEvent event2 = new LogEvent();
        event2.setLevel("ERROR");
        event2.setCategory(IncidentCategory.PAYMENT);
        event2.setMessage("PAYMENT_FAILED: timeout");

        List<Incident> incidents = detectionService.detectIncidents(List.of(event1, event2));

        assertEquals(1, incidents.size());
        assertEquals(2, incidents.get(0).getOccurrenceCount());
        assertEquals(IncidentCategory.PAYMENT, incidents.get(0).getCategory());
    }
}
