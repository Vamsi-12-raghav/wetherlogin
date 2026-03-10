package com.example.weather.auth;

public record RegisterRequest(
        String firstName,
        String secondName,
        String email,
        String username,
        String password
) {
}
