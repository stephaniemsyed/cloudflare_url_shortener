package com.stephaniemavis.cloudflare.url.shortener.data;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ShortUrl {

    public static String generateShortUrl(String longUrl) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("MD5 algorithm not found");
        }
        md.update(longUrl.getBytes());
        byte[] digest = md.digest();
        String encoded = Base64.getEncoder().encodeToString(digest);
        return encoded;
    }
}
