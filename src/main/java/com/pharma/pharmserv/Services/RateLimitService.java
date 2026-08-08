package com.pharma.pharmserv.Services;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.bucket4j.Bucket;

@Service
public class RateLimitService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private final long capacity;
    private final long refillTokens;
    private final long refillDurationSeconds;

    public RateLimitService(
            @Value("${rate-limit.capacity}") long capacity,
            @Value("${rate-limit.refill-tokens}") long refillTokens,
            @Value("${rate-limit.refill-duration-seconds}") long refillDurationSeconds) {

        this.capacity = capacity;
        this.refillTokens = refillTokens;
        this.refillDurationSeconds = refillDurationSeconds;
    }

    private Bucket createNewBucket() {
        return Bucket.builder()
                .addLimit(limit -> limit
                        .capacity(capacity)
                        .refillGreedy(
                                refillTokens,
                                Duration.ofSeconds(refillDurationSeconds)))
                .build();
    }

    public Bucket resolveBucket(String key) {
        return buckets.computeIfAbsent(
                key,
                k -> createNewBucket());
    }
}
