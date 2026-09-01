package br.com.guilherme.urlshortener.controller;

import br.com.guilherme.urlshortener.model.Url;
import br.com.guilherme.urlshortener.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import br.com.guilherme.urlshortener.exception.GlobalExceptionHandler;
import br.com.guilherme.urlshortener.exception.UrlNotFoundException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UrlControllerTest {

    private UrlService urlService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        urlService = mock(UrlService.class);

        UrlController urlController = new UrlController(urlService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(urlController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldCreateShortUrl() throws Exception {
        Url url = new Url("https://github.com/", "abc123");

        when(urlService.createShortUrl("https://github.com/"))
                .thenReturn(url);

        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "url": "https://github.com/"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originalUrl")
                        .value("https://github.com/"))
                .andExpect(jsonPath("$.shortCode")
                        .value("abc123"))
                .andExpect(jsonPath("$.clickCount")
                        .value(0));

        verify(urlService).createShortUrl("https://github.com/");
    }

    @Test
    void shouldGetUrlByShortCode() throws Exception {
        Url url = new Url("https://github.com/", "abc123");

        when(urlService.findByShortCode("abc123"))
                .thenReturn(url);

        mockMvc.perform(get("/api/urls/abc123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.originalUrl")
                        .value("https://github.com/"))
                .andExpect(jsonPath("$.shortCode")
                        .value("abc123"))
                .andExpect(jsonPath("$.clickCount")
                        .value(0));

        verify(urlService).findByShortCode("abc123");
    }

    @Test
    void shouldReturnBadRequestWhenUrlIsInvalid() throws Exception {

        mockMvc.perform(post("/api/urls")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "url": "url-invalida"
                            }
                            """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.url")
                        .value("Informe uma URL válida"));

        verifyNoInteractions(urlService);
    }

    @Test
    void shouldReturnNotFoundWhenShortCodeDoesNotExist() throws Exception {

        when(urlService.findByShortCode("invalid"))
                .thenThrow(new UrlNotFoundException("invalid"));

        mockMvc.perform(get("/api/urls/invalid"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error")
                        .value("URL não encontrada"))
                .andExpect(jsonPath("$.message")
                        .value("URL não encontrada para o código: invalid"));

        verify(urlService).findByShortCode("invalid");
    }
}