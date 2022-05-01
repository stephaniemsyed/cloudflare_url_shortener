package com.stephaniemavis.cloudflare.url.shortener.data;

import java.net.URI;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class UrlMapping {
    private final @NonNull String shortUrlId;
    private final @NonNull URI longUrl;
}
