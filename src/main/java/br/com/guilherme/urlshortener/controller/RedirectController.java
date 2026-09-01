package br.com.guilherme.urlshortener.controller;

import br.com.guilherme.urlshortener.model.Url;
import br.com.guilherme.urlshortener.service.UrlService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Redirecionamento", description = "Operações de redirecionamento de URLs")

@RestController
public class RedirectController {

    private final UrlService urlService;

    public RedirectController(UrlService urlService) {
        this.urlService = urlService;
    }

    @Operation(
            summary = "Redirecionar para a URL original",
            description = "Redireciona pelo código curto e incrementa a contagem de cliques."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "302", description = "Redirecionamento realizado"),
            @ApiResponse(responseCode = "404", description = "Código curto não encontrado")
    })

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        Url url = urlService.getByShortCode(shortCode);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", url.getOriginalUrl())
                .build();
    }
}