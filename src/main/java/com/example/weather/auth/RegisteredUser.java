package com.example.weather.auth;

public record RegisteredUser(
        String firstName,
        String secondName,
        String email,
        String username,
        String password
) {
}
