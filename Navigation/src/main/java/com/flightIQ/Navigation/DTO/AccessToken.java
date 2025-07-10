package com.flightIQ.Navigation.DTO;


import java.time.Instant;

public class AccessToken {
    private final String _token;
    private final Instant _expiration;

    public AccessToken(String token, Instant expiration) {
        _token = token;
        _expiration = expiration;
    }

    public boolean expired() {
        return Instant.now().isAfter(_expiration.minusSeconds(60));
    }

    public String getToken() {
        return _token;
    }
}