package com.example.backend.domain.auth.dto.response;

import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SignupResponse {
    private final String username;
    private final String nickname;
    private final List<UserRole> roles;

    @Builder
    public SignupResponse(String username, String nickname, List<UserRole> roles) {
        this.username = username;
        this.nickname = nickname;
        this.roles = roles;
    }

    public static SignupResponse from(User user) {
        return SignupResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .roles(new ArrayList<>(user.getRoles()))
                .build();
    }
}
