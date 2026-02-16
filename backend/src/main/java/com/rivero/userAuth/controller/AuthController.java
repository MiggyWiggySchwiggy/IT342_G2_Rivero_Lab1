package com.rivero.userAuth.controller;

import com.rivero.userAuth.model.User;
import com.rivero.userAuth.service.AuthService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true") // Updated for React
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        try {
            return ResponseEntity.ok(authService.registerUser(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest, HttpSession session) {
        try {
            // 1. Verify credentials using your existing service
            User user = authService.loginUser(loginRequest.getUsername(), loginRequest.getPasswordHash());

            // 2. Manually set the Spring Security Context
            // This tells Spring Security: "This user is now logged in"
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    Collections.emptyList()
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Store user in session as a fallback/convenience
            session.setAttribute("user", user);

            return ResponseEntity.ok(user); // Return the user object (password is hidden via @JsonIgnore)
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // Feature: Logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok("Logged out successfully");
    }
}