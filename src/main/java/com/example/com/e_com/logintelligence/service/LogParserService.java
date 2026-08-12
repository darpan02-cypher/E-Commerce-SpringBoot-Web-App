package com.example.com.e_com.logintelligence.service;

import com.example.com.e_com.logintelligence.model.LogEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class LogParserService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<LogEvent> parseEvents(String logFileName) {
        Path path = Paths.get(logFileName);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        try (var lines = Files.lines(path)) {
            return lines.map(this::parseEventLine)
                    .filter(java.util.Objects::nonNull)
                    .toList();
        } catch (IOException ex) {
            return new ArrayList<>();
        }
    }

    private LogEvent parseEventLine(String line) {
        try {
            return objectMapper.readValue(line, LogEvent.class);
        } catch (IOException ex) {
            return null;
        }
    }
}
