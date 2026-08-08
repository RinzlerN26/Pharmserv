package com.pharma.pharmserv.Filter;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pharma.pharmserv.Services.RateLimitService;

import io.github.bucket4j.Bucket;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class UserRateLimitFilter extends OncePerRequestFilter {

    private final RateLimitService rateLimitService;

    public UserRateLimitFilter(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws ServletException, IOException {

        if (!request.getRequestURI().startsWith("/ms/pharma/")) {
            filterChain.doFilter(request, response);
            return;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated()) {

            filterChain.doFilter(request, response);
            return;
        }

        String userStringId = authentication.getName();

        Bucket bucket = rateLimitService.resolveBucket("user:" + userStringId);

        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
            return;
        }

        response.setStatus(429);
        response.setContentType("application/json");

        response.getWriter().write("""
                {
                    "status": 429,
                    "message": "Too many requests. Please try again later."
                }
                """);
    }
}