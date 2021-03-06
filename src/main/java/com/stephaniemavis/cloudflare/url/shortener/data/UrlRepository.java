package com.stephaniemavis.cloudflare.url.shortener.data;

import java.net.URI;
import java.util.List;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.time.OffsetDateTime;

public class UrlRepository {
    private HashMap<URI,String> urlMappingsByLongUrl = new HashMap<>();
    private HashMap<String,URI> urlMappingsByShortUrlId = new HashMap<>();
    private HashMap<String, List<OffsetDateTime>> urlAccess = new HashMap<>();

    public boolean contains(URI longUrl){
        return urlMappingsByLongUrl.containsKey(longUrl);
    }

    public boolean contains(String shortUrlId){
        return urlMappingsByShortUrlId.containsKey(shortUrlId)
            && urlMappingsByLongUrl.containsKey(urlMappingsByShortUrlId.get(shortUrlId));
    }

    public void addUrlMapping(String shortUrlId, URI longUrl){
        urlMappingsByLongUrl.put(longUrl, shortUrlId);
        urlMappingsByShortUrlId.put(shortUrlId, longUrl);
    }

    public String getShortUrlId(URI longUrl){
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

    public void logAccess(String shortUrlId){
        if(urlAccess.containsKey(shortUrlId)){
            urlAccess.get(shortUrlId).add(OffsetDateTime.now());
        }else{
            List<OffsetDateTime> list = new ArrayList<OffsetDateTime>();
            list.add(OffsetDateTime.now());
            urlAccess.put(shortUrlId, list);
        }
    }

    public List<OffsetDateTime> getUsageInfo(String shortUrlId) {
        List<OffsetDateTime> usageList = urlAccess.get(shortUrlId);
        if(usageList == null){
            return ImmutableList.of();
        }
        return usageList;
    }
    
}
