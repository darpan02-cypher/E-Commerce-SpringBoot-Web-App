package com.example.com.e_com.logintelligence.service;

import com.example.com.e_com.logintelligence.model.LogEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LogParserServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void parsesJsonLogLineIntoLogEvent() throws Exception {
        String json = "{\"timestamp\":\"2026-08-11T12:34:56.789Z\",\"level\":\"INFO\",\"message\":\"Test event\",\"mdc\":{\"requestId\":\"abc-123\",\"event\":\"TEST_EVENT\"}}";
        Path file = tempDir.resolve("log.json");
        Files.writeString(file, json + System.lineSeparator());

        LogParserService parser = new LogParserService();
        List<LogEvent> events = parser.parseEvents(file.toString());

        assertEquals(1, events.size());
        LogEvent event = events.get(0);
        assertNotNull(event.getTimestamp());
        assertEquals("INFO", event.getLevel());
        assertEquals("Test event", event.getMessage());
        assertEquals("abc-123", event.getRequestId());
        assertEquals("TEST_EVENT", event.getEvent());
    }
}
