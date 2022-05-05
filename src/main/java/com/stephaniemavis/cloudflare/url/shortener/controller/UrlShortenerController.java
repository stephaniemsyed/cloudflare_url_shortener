package com.stephaniemavis.cloudflare.url.shortener.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.stephaniemavis.cloudflare.url.shortener.data.ShortUrl;
import com.stephaniemavis.cloudflare.url.shortener.data.UrlRepository;
import com.stephaniemavis.cloudflare.url.shortener.data.UsageStats;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UrlShortenerController {
    private final UrlRepository repository;

    UrlShortenerController(UrlRepository repository){
        this.repository = repository;
    }


    @PutMapping("/shorten")
    String createOrFetchUrlMapping(@RequestBody String longUrl) throws URISyntaxException{
        URI longUrlUri = new URI(longUrl);
        String shortUrlId = repository.getShortUrlId(longUrlUri);
        if(shortUrlId == null){
            shortUrlId  = ShortUrl.generateShortUrl(longUrl);
            repository.addUrlMapping(shortUrlId, longUrlUri);
        }

        return shortUrlId;
    }

    @DeleteMapping("/delete/{shortUrlId}")
    void deleteUrlMapping(@PathVariable(name="shortUrlId") String shortUrlId){
        repository.deleteMapping(shortUrlId);
    }

    @GetMapping("/link/{shortUrlId}")
    ResponseEntity<Void> handleRedirect(@PathVariable(name="shortUrlId") String shortUrlId){
        URI destination = repository.getLongUrl(shortUrlId);
        if(destination == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.FOUND).location(destination).build();
    }

    @GetMapping("/usage/{shortUrlId}")
    UsageStats getUsageInfo(@PathVariable(name="shortUrlId") String shortUrlId){
       return getUsageStatsFromList(repository.getUsageInfo(shortUrlId));
    }

    private UsageStats getUsageStatsFromList(List<OffsetDateTime> list){
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime pastDay = now.minusDays(1);
        OffsetDateTime pastWeek = now.minusWeeks(1);
        int hitsPastDay = list.stream().filter(((t) -> t.isAfter(pastDay))).collect(Collectors.toList()).size();
        int hitsPastWeek = list.stream().filter(((t) -> t.isAfter(pastWeek))).collect(Collectors.toList()).size();
        return new UsageStats(hitsPastDay, hitsPastWeek, list.size());
    
    }
}