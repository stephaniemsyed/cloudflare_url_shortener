package com.stephaniemavis.cloudflare.url.shortener.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.List;

import com.stephaniemavis.cloudflare.url.shortener.data.ShortUrl;
import com.stephaniemavis.cloudflare.url.shortener.data.UrlRepository;

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
    List<OffsetDateTime> getUsageInfo(@PathVariable(name="shortUrlId") String shortUrlId){
        return repository.getUsageInfo(shortUrlId);
    }
}