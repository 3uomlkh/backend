package com.example.backend.domain.auth.controller;

import com.example.backend.domain.auth.dto.request.SignupRequest;
import com.example.backend.domain.auth.dto.response.SignupResponse;
import com.example.backend.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth/signup")
    public ResponseEntity<SignupResponse> signup (@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }
}
