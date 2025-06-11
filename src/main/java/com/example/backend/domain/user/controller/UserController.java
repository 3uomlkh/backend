package com.example.backend.domain.user.controller;

import com.example.backend.domain.user.dto.response.GrantAdminResponse;
import com.example.backend.domain.user.enums.UserRole;
import com.example.backend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/admin/users/{userId}/roles")
    @Secured(UserRole.Authority.ADMIN)
    public ResponseEntity<GrantAdminResponse> grantAdmin(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(userService.grantAdmin(userId));
    }
}
