package br.com.guilherme.urlshortener.controller;

import br.com.guilherme.urlshortener.dto.CreateUrlRequest;
import br.com.guilherme.urlshortener.dto.UrlResponse;
import br.com.guilherme.urlshortener.model.Url;
import br.com.guilherme.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<UrlResponse> createShortUrl(
            @Valid @RequestBody CreateUrlRequest request) {

        Url url = urlService.createShortUrl(request.getUrl());

        UrlResponse response = new UrlResponse(
                url.getOriginalUrl(),
                url.getShortCode(),
                "http://localhost:8080/" + url.getShortCode(),
                url.getClickCount()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<UrlResponse> getUrl(
            @PathVariable String shortCode) {

        Url url = urlService.findByShortCode(shortCode);

        UrlResponse response = new UrlResponse(
                url.getOriginalUrl(),
                url.getShortCode(),
                "http://localhost:8080/" + url.getShortCode(),
                url.getClickCount()
        );

        return ResponseEntity.ok(response);
    }
}