package br.com.guilherme.urlshortener.controller;

import br.com.guilherme.urlshortener.dto.CreateUrlRequest;
import br.com.guilherme.urlshortener.dto.UrlResponse;
import br.com.guilherme.urlshortener.model.Url;
import br.com.guilherme.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "URLs", description = "Operações para encurtamento e consulta de URLs")

@RestController
@RequestMapping("/api/urls")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @Operation(
            summary = "Encurtar uma URL",
            description = "Recebe uma URL original e gera um código curto único."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "URL encurtada com sucesso"),
            @ApiResponse(responseCode = "400", description = "URL informada é inválida")
    })

    @PostMapping
    public ResponseEntity<UrlResponse> createShortUrl(
            @Valid @RequestBody CreateUrlRequest request) {

        Url url = urlService.createShortUrl(request.getUrl());

        UrlResponse response = new UrlResponse(
                url.getOriginalUrl(),
                url.getShortCode(),
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/")
                        .path(url.getShortCode())
                        .toUriString(),
                url.getClickCount()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Consultar uma URL",
            description = "Retorna as informações de uma URL a partir do código curto."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "URL encontrada"),
            @ApiResponse(responseCode = "404", description = "Código curto não encontrado")
    })

    @GetMapping("/{shortCode}")
    public ResponseEntity<UrlResponse> getUrl(
            @PathVariable String shortCode) {

        Url url = urlService.findByShortCode(shortCode);

        UrlResponse response = new UrlResponse(
                url.getOriginalUrl(),
                url.getShortCode(),
                ServletUriComponentsBuilder
                        .fromCurrentContextPath()
                        .path("/")
                        .path(url.getShortCode())
                        .toUriString(),
                url.getClickCount()
        );

        return ResponseEntity.ok(response);
    }
}