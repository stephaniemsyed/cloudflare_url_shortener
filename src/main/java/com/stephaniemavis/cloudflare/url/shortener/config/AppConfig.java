package com.stephaniemavis.cloudflare.url.shortener.config;

import com.stephaniemavis.cloudflare.url.shortener.data.UrlRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean 
    public UrlRepository urlRepository(){
        return new UrlRepository();
    }
}
