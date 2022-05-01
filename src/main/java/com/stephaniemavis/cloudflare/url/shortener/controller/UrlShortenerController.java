package com.stephaniemavis.cloudflare.url.shortener.controller;

import java.net.URI;
import java.net.URISyntaxException;

import com.stephaniemavis.cloudflare.url.shortener.data.UrlMapping;
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
    UrlMapping createOrFetchUrlMapping(@RequestBody String longUrl) throws URISyntaxException{
        URI longUrlUri = new URI(longUrl);
        UrlMapping mapping = repository.getMapping(longUrlUri);
        if(mapping == null){
            mapping  = new UrlMapping(longUrl, longUrlUri);
            repository.addUrlMapping(mapping);
        }

        return mapping;
    }

    @DeleteMapping("/delete/{id}")
    void deleteUrlMapping(@PathVariable String shortUrlId){
        repository.deleteMapping(shortUrlId);
    }

    @GetMapping("/link/{id}")
    ResponseEntity<Void> handleRedirect(@PathVariable String shortUrlId){
        return ResponseEntity.status(HttpStatus.FOUND).location(repository.getLongUrl(shortUrlId)).build();
    }

}