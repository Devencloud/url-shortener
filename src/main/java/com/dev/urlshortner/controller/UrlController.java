package com.dev.urlshortner.controller;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dev.urlshortner.model.ShortenRequest;
import com.dev.urlshortner.service.UrlService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;



@Tag(name = "URL Shortener API", description = "API for shortening and redirecting URLs")
@RestController
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;


    @Operation(summary = "Shorten a URL")
    @PostMapping("/api/shorten")
    public String shorten(@Valid @RequestBody ShortenRequest request) {
        String shortKey = urlService.shortenUrl(request.getOriginalUrl());
        return "http://localhost:8080/r/" + shortKey;
    }

    @Operation(summary = "Redirect to original URL")
    @GetMapping("/r/{shortKey}")
    public void redirect(@PathVariable String shortKey, HttpServletResponse response) throws IOException {
       
     
        String originalUrl = urlService.getOriginalUrl(shortKey);
        if (originalUrl != null) {
            response.sendRedirect(originalUrl);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("Short URL not found");

        }
    }

}
