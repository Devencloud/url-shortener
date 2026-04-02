package com.dev.urlshortner.service;

import java.util.List;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RateLimitService {

    private final StringRedisTemplate redisTemplate;

    private static final int MAX_REQUESTS = 10;
    private static final long WINDOW_SECONDS = 60;

    // Atomic increment + expire in one Redis call — no race condition
    private static final DefaultRedisScript<Long> RATE_LIMIT_SCRIPT = new DefaultRedisScript<>("""
            local count = redis.call('INCR', KEYS[1])
            if count == 1 then
                redis.call('EXPIRE', KEYS[1], ARGV[1])
            end
            return count
            """, Long.class);

    public boolean isAllowed(String ipAddress) {
        String key = "rate_limit:" + ipAddress;
        Long count = redisTemplate.execute(RATE_LIMIT_SCRIPT, List.of(key), String.valueOf(WINDOW_SECONDS));
        return count != null && count <= MAX_REQUESTS;
    }
}

