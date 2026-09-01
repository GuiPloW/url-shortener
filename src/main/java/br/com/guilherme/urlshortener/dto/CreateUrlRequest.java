package br.com.guilherme.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

public class CreateUrlRequest {

    @NotBlank(message = "A URL é obrigatória")
    @URL(message = "Informe uma URL válida")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}