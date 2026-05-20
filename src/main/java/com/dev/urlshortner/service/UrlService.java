package com.dev.urlshortner.service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.dev.urlshortner.entity.Url;
import com.dev.urlshortner.repository.UrlRepository;
import com.dev.urlshortner.util.Base62Encoder;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UrlService {

  private final UrlRepository urlRepository;
  private final StringRedisTemplate redisTemplate;

  @Transactional
  public String shortenUrl(String originalUrl) {
    Optional<Url> existing = urlRepository.findByOriginalUrl(originalUrl);
    if (existing.isPresent()) {
      return existing.get().getShortKey();
    }
    Url url = new Url();
    url.setOriginalUrl(originalUrl);
    url = urlRepository.save(url);
    String shortKey = Base62Encoder.encode(url.getId());
    if (shortKey == null || shortKey.isEmpty()) {
      throw new RuntimeException("Error generating short key");
    }
    url.setShortKey(shortKey);

    return shortKey;

  }

  public String getOriginalUrl(String shortKey) {
    try {
      String cachedUrl = redisTemplate.opsForValue().get(shortKey);
      if (cachedUrl != null) {
        System.out.println("Cache hit:");
        return cachedUrl;
      }
    } catch (Exception e) {
      System.out.println("Redis unavailable, falling back to DB: " + e.getMessage());
    }

    System.out.println("Cache miss: Go to DB");
    Optional<Url> url = urlRepository.findByShortKey(shortKey);
    if (url.isPresent()) {
      String originalUrl = url.get().getOriginalUrl();
      try {
        redisTemplate.opsForValue().set(shortKey, originalUrl, 10, TimeUnit.MINUTES);
      } catch (Exception e) {
        System.out.println("Redis write failed: " + e.getMessage());
      }
      return originalUrl;
    }
    return null;
  }

}
