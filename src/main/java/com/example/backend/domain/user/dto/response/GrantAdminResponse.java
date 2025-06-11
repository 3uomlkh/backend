package com.example.backend.domain.user.dto.response;

import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GrantAdminResponse {
    private final String username;
    private final String nickname;
    private final List<UserRole> roles;

    @Builder
    public GrantAdminResponse(String username, String nickname, List<UserRole> roles) {
        this.username = username;
        this.nickname = nickname;
        this.roles = roles;
    }

    public static GrantAdminResponse from(User user) {
        return GrantAdminResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .roles(new ArrayList<>(user.getRoles()))
                .build();
    }
}
