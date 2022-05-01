package com.stephaniemavis.cloudflare.url.shortener.controller;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import java.net.URLConnection;

import com.stephaniemavis.cloudflare.url.shortener.data.UrlRepository;

import org.junit.jupiter.api.Test;

public class UrlShortenerControllerTest {

    UrlRepository mockRepository = mock(UrlRepository.class);
    
    UrlShortenerController controller = new UrlShortenerController(mockRepository);

    @Test
    public void deleteNonexistantMapping(){

    }

    @Test
    public void deleteExistantMapping(){
        
    }


    @Test
    public void putMappingInvalidLongUrl(){

    }

    @Test
    public void putMappingAlreadyExists(){

    }

    @Test
    public void putMappingValidUrl(){

    }

    @Test
    public void handleRedirectMappingExists(){

    }

    @Test
    public void handleRedirectMappingDoesNotExist(){

    }

    @Test
    public void handleRedirectBadShortUrlId(){
        
    }

}
