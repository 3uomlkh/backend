package com.example.backend.domain.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequest {
    @NotBlank(message = "이름 입력은 필수입니다.")
    private String username;
    @NotBlank(message = "비밀번호 입력은 필수입니다.")
    private String password;
    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;
}
