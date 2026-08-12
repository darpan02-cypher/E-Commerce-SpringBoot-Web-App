package com.example.com.e_com.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

public class RequestIdFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(RequestIdFilter.class);
    private static final String REQUEST_ID_KEY = "requestId";
    private static final String HTTP_METHOD_KEY = "httpMethod";
    private static final String ENDPOINT_KEY = "endpoint";
    private static final String STATUS_KEY = "status";
    private static final String EVENT_KEY = "event";
    private static final String SERVICE_KEY = "service";
    private static final long SLOW_REQUEST_THRESHOLD_MS = 1000L;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestId = request.getHeader("X-Request-Id");
        if (requestId == null || requestId.isBlank()) {
            requestId = UUID.randomUUID().toString();
        }

        MDC.put(REQUEST_ID_KEY, requestId);
        MDC.put(HTTP_METHOD_KEY, request.getMethod());
        MDC.put(ENDPOINT_KEY, request.getRequestURI());
        MDC.put(SERVICE_KEY, "ecommerce-backend");

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationMs = System.currentTimeMillis() - startTime;
            MDC.put(STATUS_KEY, String.valueOf(response.getStatus()));

            if (durationMs > SLOW_REQUEST_THRESHOLD_MS) {
                MDC.put(EVENT_KEY, "SLOW_REQUEST");
                logger.warn("SLOW_REQUEST detected for {} {} durationMs={}", request.getMethod(), request.getRequestURI(), durationMs);
                MDC.remove(EVENT_KEY);
            }

            logger.info("REQUEST_COMPLETED {} {} status={} durationMs={}", request.getMethod(), request.getRequestURI(), response.getStatus(), durationMs);
            response.setHeader("X-Request-Id", requestId);
            MDC.remove(REQUEST_ID_KEY);
            MDC.remove(HTTP_METHOD_KEY);
            MDC.remove(ENDPOINT_KEY);
            MDC.remove(STATUS_KEY);
            MDC.remove(SERVICE_KEY);
        }
    }
}
