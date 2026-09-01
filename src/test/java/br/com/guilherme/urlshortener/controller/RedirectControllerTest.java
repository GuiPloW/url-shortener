package br.com.guilherme.urlshortener.controller;

import br.com.guilherme.urlshortener.model.Url;
import br.com.guilherme.urlshortener.service.UrlService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RedirectControllerTest {

    private UrlService urlService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        urlService = mock(UrlService.class);

        RedirectController redirectController =
                new RedirectController(urlService);

        mockMvc = MockMvcBuilders
                .standaloneSetup(redirectController)
                .build();
    }

    @Test
    void shouldRedirectToOriginalUrl() throws Exception {
        Url url = new Url("https://github.com/", "abc123");

        when(urlService.getByShortCode("abc123"))
                .thenReturn(url);

        mockMvc.perform(get("/abc123"))
                .andExpect(status().isFound())
                .andExpect(header().string(
                        "Location",
                        "https://github.com/"
                ));

        verify(urlService).getByShortCode("abc123");
    }
}