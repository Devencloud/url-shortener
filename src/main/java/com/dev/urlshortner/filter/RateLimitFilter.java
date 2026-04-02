package com.dev.urlshortner.filter;

import java.io.IOException;

import org.springframework.web.filter.OncePerRequestFilter;
import com.dev.urlshortner.service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@RequiredArgsConstructor
public class RateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String ip = getClientIp(request);

        try {
            if (!rateLimitService.isAllowed(ip)) {
                response.setStatus(429);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Too many requests. Max 10 requests per minute.\"}");
                return;
            }
        } catch (Exception e) {
            // If Redis is down, let the request through and log the error
            log.error("Rate limit check failed for IP {}: {}", ip, e.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        // Handles the case where app is behind a proxy/load balancer
        String forwarded = request.getHeader("X-Forwarded-For");
        if (forwarded != null && !forwarded.isBlank()) {
            return forwarded.split(",")[0].trim(); // Take the first IP
        }
        return request.getRemoteAddr();
    }
}