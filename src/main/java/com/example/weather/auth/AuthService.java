package com.example.weather.auth;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final Map<String, RegisteredUser> users = new ConcurrentHashMap<>();

    public boolean register(RegisterRequest request) {
        String username = request.username().trim();
        if (users.containsKey(username)) {
            return false;
        }

        RegisteredUser registeredUser = new RegisteredUser(
                request.firstName().trim(),
                request.secondName().trim(),
                request.email().trim(),
                username,
                request.password()
        );

        users.put(username, registeredUser);
        return true;
    }

    public boolean login(LoginRequest request) {
        String username = request.username().trim();
        RegisteredUser user = users.get(username);
        if (user == null) {
            return false;
        }

        return user.password().equals(request.password());
    }

    public boolean hasUsername(String username) {
        return username != null && users.containsKey(username.trim());
    }

    public boolean resetPassword(ForgotPasswordRequest request) {
        String username = request.username().trim();
        RegisteredUser existingUser = users.get(username);

        if (existingUser == null) {
            return false;
        }

        if (!existingUser.email().equalsIgnoreCase(request.email().trim())) {
            return false;
        }

        RegisteredUser updatedUser = new RegisteredUser(
                existingUser.firstName(),
                existingUser.secondName(),
                existingUser.email(),
                existingUser.username(),
                request.newPassword()
        );

        users.put(username, updatedUser);
        return true;
    }
}
