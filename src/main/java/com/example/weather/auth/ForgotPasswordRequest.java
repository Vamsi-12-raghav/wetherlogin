package com.example.weather.auth;

public record ForgotPasswordRequest(String username, String email, String newPassword) {
}
