package com.dev.urlshortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dev.urlshortner.entity.Url;


@Repository
public interface UrlRepository extends JpaRepository<Url,Long> {
    Optional<Url> findByShortKey(String shortKey);
    Optional<Url> findByOriginalUrl(String originalUrl);
    
}
