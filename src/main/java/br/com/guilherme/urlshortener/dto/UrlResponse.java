package br.com.guilherme.urlshortener.dto;

public class UrlResponse {

    private String originalUrl;
    private String shortCode;
    private String shortUrl;
    private Long clickCount;

    public UrlResponse(String originalUrl, String shortCode,
                       String shortUrl, Long clickCount) {
        this.originalUrl = originalUrl;
        this.shortCode = shortCode;
        this.shortUrl = shortUrl;
        this.clickCount = clickCount;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortCode() {
        return shortCode;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public Long getClickCount() {
        return clickCount;
    }
}