package com.stephaniemavis.cloudflare.url.shortener.data;

import java.net.URI;
import java.util.HashMap;

public class UrlRepository {
    private HashMap<URI,UrlMapping> urlMappingsByLongUrl = new HashMap<>();
    private HashMap<String,URI> urlMappingsByShortUrlId = new HashMap<>();

    public boolean contains(URI longUrl){
        return urlMappingsByLongUrl.containsKey(longUrl);
    }

    public boolean contains(String shortUrlId){
        return urlMappingsByShortUrlId.containsKey(shortUrlId)
            && urlMappingsByLongUrl.containsKey(urlMappingsByShortUrlId.get(shortUrlId));
    }

    public void addUrlMapping(UrlMapping urlMapping){
        urlMappingsByLongUrl.put(urlMapping.longUrl(), urlMapping);
        urlMappingsByShortUrlId.put(urlMapping.shortUrlId(), urlMapping.longUrl());
    }

    public UrlMapping getMapping(URI longUrl){
        return urlMappingsByLongUrl.get(longUrl);
    }

    public void deleteMapping(String shortUrlId){
        URI longURL = urlMappingsByShortUrlId.get(shortUrlId);
        if(longURL == null){
            return;
        }

        urlMappingsByLongUrl.remove(longURL);
        
    }
    
    public URI getLongUrl(String shortUrlId){
        return urlMappingsByShortUrlId.get(shortUrlId);

    }
}
