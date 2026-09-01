package br.com.guilherme.urlshortener.service;

import br.com.guilherme.urlshortener.exception.UrlNotFoundException;
import br.com.guilherme.urlshortener.model.Url;
import br.com.guilherme.urlshortener.repository.UrlRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UrlServiceTest {

    @Mock
    private UrlRepository urlRepository;

    private UrlService urlService;

    @BeforeEach
    void setUp() {
        urlService = new UrlService(urlRepository);
    }

    @Test
    void shouldCreateShortUrl() {
        String originalUrl = "https://github.com/";

        when(urlRepository.existsByShortCode(anyString()))
                .thenReturn(false);

        when(urlRepository.save(any(Url.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Url result = urlService.createShortUrl(originalUrl);

        assertEquals(originalUrl, result.getOriginalUrl());
        assertNotNull(result.getShortCode());
        assertEquals(6, result.getShortCode().length());
        assertEquals(0L, result.getClickCount());

        verify(urlRepository).save(any(Url.class));
    }

    @Test
    void shouldFindUrlByShortCode() {
        Url url = new Url("https://github.com/", "abc123");

        when(urlRepository.findByShortCode("abc123"))
                .thenReturn(Optional.of(url));

        Url result = urlService.findByShortCode("abc123");

        assertEquals("https://github.com/", result.getOriginalUrl());
        assertEquals("abc123", result.getShortCode());
    }

    @Test
    void shouldThrowExceptionWhenShortCodeDoesNotExist() {
        when(urlRepository.findByShortCode("invalid"))
                .thenReturn(Optional.empty());

        assertThrows(
                UrlNotFoundException.class,
                () -> urlService.findByShortCode("invalid")
        );
    }

    @Test
    void shouldIncrementClickCountWhenRedirecting() {
        Url url = new Url("https://github.com/", "abc123");

        when(urlRepository.findByShortCode("abc123"))
                .thenReturn(Optional.of(url));

        when(urlRepository.save(any(Url.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Url result = urlService.getByShortCode("abc123");

        assertEquals(1L, result.getClickCount());

        verify(urlRepository).save(url);
    }
}