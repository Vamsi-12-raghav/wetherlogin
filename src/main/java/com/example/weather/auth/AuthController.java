package com.example.weather.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        if (!isValidRegistration(request)) {
            return ResponseEntity.badRequest().body(new ApiResponse("Please provide valid registration details."));
        }

        if (authService.hasUsername(request.username())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("Username already exists."));
        }

        boolean created = authService.register(request);
        if (!created) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("Username already exists."));
        }

        return ResponseEntity.ok(new ApiResponse("Registration successful."));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        if (!isValidLogin(request)) {
            return ResponseEntity.badRequest().body(new ApiResponse("Username and password are required."));
        }

        boolean authenticated = authService.login(request);
        if (!authenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Invalid username or password."));
        }

        return ResponseEntity.ok(new ApiResponse("Login successful."));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        if (!isValidForgotPassword(request)) {
            return ResponseEntity.badRequest().body(new ApiResponse("Please provide valid reset details."));
        }

        boolean updated = authService.resetPassword(request);
        if (!updated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse("Username and email do not match."));
        }

        return ResponseEntity.ok(new ApiResponse("Password updated successfully."));
    }

    private boolean isValidRegistration(RegisterRequest request) {
        if (request == null) {
            return false;
        }

        return hasText(request.firstName())
                && hasText(request.secondName())
                && hasText(request.email())
                && request.email().contains("@")
                && hasText(request.username())
                && hasText(request.password())
                && request.username().trim().length() >= 4
                && request.password().length() >= 8;
    }

    private boolean isValidLogin(LoginRequest request) {
        return request != null && hasText(request.username()) && hasText(request.password());
    }

    private boolean isValidForgotPassword(ForgotPasswordRequest request) {
        return request != null
                && hasText(request.username())
                && hasText(request.email())
                && request.email().contains("@")
                && hasText(request.newPassword())
                && request.newPassword().length() >= 8;
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
