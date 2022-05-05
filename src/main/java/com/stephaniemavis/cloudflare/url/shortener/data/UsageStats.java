package com.stephaniemavis.cloudflare.url.shortener.data;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

@Data
@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
@Jacksonized
@Builder
public class UsageStats {
    @JsonProperty("Past Day")
    private final int hitsPastDay;

    @JsonProperty("Past Week")
    private final int hitsPastWeek;
    
    @JsonProperty("Lifetime")
    private final int hitsLifetime;
}
