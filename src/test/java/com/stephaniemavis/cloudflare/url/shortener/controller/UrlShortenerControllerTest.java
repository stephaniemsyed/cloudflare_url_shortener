package com.stephaniemavis.cloudflare.url.shortener.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.google.common.collect.ImmutableList;
import com.stephaniemavis.cloudflare.url.shortener.data.ShortUrl;
import com.stephaniemavis.cloudflare.url.shortener.data.UrlRepository;
import com.stephaniemavis.cloudflare.url.shortener.data.UsageStats;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.List;

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
        final String shortUrlId = ShortUrl.generateShortUrl("http://www.google.com");
        URI mockURI = mock(URI.class);
        when(mockRepository.getLongUrl(shortUrlId)).thenReturn(mockURI);
        ResponseEntity<Void> result = controller.handleRedirect(shortUrlId);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

    @Test
    public void handleRedirectMappingDoesNotExist(){
        final String shortUrlId = ShortUrl.generateShortUrl("http://www.google.com");
        when(mockRepository.getLongUrl(shortUrlId)).thenReturn(null);
        ResponseEntity<Void> result = controller.handleRedirect(shortUrlId);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test 
    public void getUsageStats(){
        final String shortUrlId = ShortUrl.generateShortUrl("http://www.google.com");
        final List<OffsetDateTime> times = ImmutableList.of(
            OffsetDateTime.now().minusHours(6),
            OffsetDateTime.now().minusHours(7),
            OffsetDateTime.now().minusDays(3),
            OffsetDateTime.now().minusDays(4),
            OffsetDateTime.now().minusDays(5),
            OffsetDateTime.now().minusWeeks(5),
            OffsetDateTime.now().minusWeeks(10),
            OffsetDateTime.now().minusWeeks(15),
            OffsetDateTime.now().minusWeeks(20),
            OffsetDateTime.now().minusWeeks(25),
            OffsetDateTime.now().minusWeeks(30),
            OffsetDateTime.now().minusWeeks(35),
            OffsetDateTime.now().minusWeeks(40)
        );
        final int expectedPastDay = 2;
        final int expectedPastWeek = 5;
        final int expectedLifeTime = times.size();

        when(mockRepository.getUsageInfo(shortUrlId)).thenReturn(times);
        UsageStats result = controller.getUsageInfo(shortUrlId);
        assertNotNull(result);
        assertEquals(expectedPastDay, result.hitsPastDay());
        assertEquals(expectedPastWeek, result.hitsPastWeek());
        assertEquals(expectedLifeTime, result.hitsLifetime());
    }

    @Test 
    public void getUsageStatsForMissingShortUrlId(){
        final String shortUrlId = ShortUrl.generateShortUrl("http://www.google.com");
        final List<OffsetDateTime> times = ImmutableList.of();
        final int expectedPastDay = 0;
        final int expectedPastWeek = 0;
        final int expectedLifeTime = times.size();

        when(mockRepository.getUsageInfo(shortUrlId)).thenReturn(times);
        UsageStats result = controller.getUsageInfo(shortUrlId);
        assertNotNull(result);
        assertEquals(expectedPastDay, result.hitsPastDay());
        assertEquals(expectedPastWeek, result.hitsPastWeek());
        assertEquals(expectedLifeTime, result.hitsLifetime());
    }
}
