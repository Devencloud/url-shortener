package com.dev.urlshortner.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.dev.urlshortner.filter.RateLimitFilter;
import com.dev.urlshortner.service.RateLimitService;

@Configuration
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.password:}")
    private String password;

    @Value("${spring.data.redis.ssl.enabled:false}")
    private boolean sslEnabled;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(host, port);
        if (!password.isEmpty()) {
            config.setPassword(password);
        }
        LettuceClientConfiguration clientConfig;
        if (sslEnabled) {
            clientConfig = LettuceClientConfiguration.builder()
                    .useSsl().disablePeerVerification()
                    .build();
        } else {
            clientConfig = LettuceClientConfiguration.builder().build();
        }
        return new LettuceConnectionFactory(config, clientConfig);
    }

    @Bean
    public StringRedisTemplate redisTemplate(LettuceConnectionFactory connectionFactory) {
        return new StringRedisTemplate(connectionFactory);
    }

    @Bean
    public FilterRegistrationBean<RateLimitFilter> rateLimitFilter(RateLimitService rateLimitService) {
        RateLimitFilter filter = new RateLimitFilter(rateLimitService);
        FilterRegistrationBean<RateLimitFilter> registration = new FilterRegistrationBean<>(filter);
        registration.addUrlPatterns("/*");
        registration.setOrder(1);
        return registration;
    }
}