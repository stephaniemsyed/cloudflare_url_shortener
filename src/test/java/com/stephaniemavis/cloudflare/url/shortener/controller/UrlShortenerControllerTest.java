package com.stephaniemavis.cloudflare.url.shortener.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.stephaniemavis.cloudflare.url.shortener.data.ShortUrl;
import com.stephaniemavis.cloudflare.url.shortener.data.UrlRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UrlShortenerControllerTest {

    UrlRepository mockRepository = mock(UrlRepository.class);
    
    UrlShortenerController controller = new UrlShortenerController(mockRepository);

    @Test
    public void putMappingInvalidLongUrl(){
        final String invalidUrl = "this is not a url";
        assertThrows(URISyntaxException.class, () -> controller.createOrFetchUrlMapping(invalidUrl));
    }

    @Test
    public void putMappingAlreadyExists() throws URISyntaxException{
        final String longUrl = "https://www.google.com/";
        final String shortUrlId = ShortUrl.generateShortUrl(longUrl);
        when(mockRepository.getShortUrlId(any(URI.class))).thenReturn(shortUrlId);

        assertEquals(shortUrlId, controller.createOrFetchUrlMapping(longUrl));
    }

    @Test
    public void putMappingValidUrl() throws URISyntaxException{
        final String longUrl = "https://www.google.com/";
        when(mockRepository.getShortUrlId(any(URI.class))).thenReturn(null);
        final String result = controller.createOrFetchUrlMapping(longUrl);
        assertFalse(result==null);
        assertFalse(result.isEmpty());
        assertFalse(result.trim().isEmpty());
    }

    @Test
    public void handleRedirectMappingExists(){
        final String shortUrlId = UUID.randomUUID().toString();
        URI mockURI = mock(URI.class);
        when(mockRepository.getLongUrl(shortUrlId)).thenReturn(mockURI);
        ResponseEntity<Void> result = controller.handleRedirect(shortUrlId);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
        // assertEquals(mockURI, result.getHeaders().getLocation());
    }

    @Test
    public void handleRedirectMappingDoesNotExist(){
        final String shortUrlId = UUID.randomUUID().toString();
        when(mockRepository.getLongUrl(shortUrlId)).thenReturn(null);
        ResponseEntity<Void> result = controller.handleRedirect(shortUrlId);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }
}
