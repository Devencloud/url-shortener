package com.dev.urlshortner.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class ShortenRequest {
    @NotBlank(message = "URL cannot be empty")
    @Pattern(regexp = "^(https?://).+", message = "Invalid URL format")
    private String originalUrl;

    public String getOriginalUrl() {
        return originalUrl;

    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
