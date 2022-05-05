package com.stephaniemavis.cloudflare.url.shortener.data;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@RequiredArgsConstructor
@Accessors(fluent = true) @Getter
public class UsageStats {
    private final int hitsPastDay;
    private final int hitsPastWeek;
    private final int hitsLifetime;
}
