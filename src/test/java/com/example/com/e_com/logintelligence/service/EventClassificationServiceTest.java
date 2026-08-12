package com.example.com.e_com.logintelligence.service;

import com.example.com.e_com.logintelligence.model.IncidentCategory;
import com.example.com.e_com.logintelligence.model.LogEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventClassificationServiceTest {

    @Test
    void classifiesPaymentFailureAsPaymentCategory() {
        LogEvent event = new LogEvent();
        event.setLevel("ERROR");
        event.setMessage("Payment processing failed due to gateway error");

        EventClassificationService classifier = new EventClassificationService();

        assertEquals(IncidentCategory.PAYMENT, classifier.classify(event));
    }

    @Test
    void classifiesSlowRequestAsPerformanceCategory() {
        LogEvent event = new LogEvent();
        event.setLevel("WARN");
        event.setMessage("Request durationMs=1500 exceeded threshold");

        EventClassificationService classifier = new EventClassificationService();

        assertEquals(IncidentCategory.PERFORMANCE, classifier.classify(event));
    }
}
