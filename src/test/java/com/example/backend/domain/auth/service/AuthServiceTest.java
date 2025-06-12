package com.example.backend.domain.auth.service;

import com.example.backend.domain.auth.dto.request.SigninRequest;
import com.example.backend.domain.auth.dto.request.SignupRequest;
import com.example.backend.domain.auth.dto.response.SigninResponse;
import com.example.backend.domain.auth.dto.response.SignupResponse;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.enums.UserRole;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.domain.user.service.UserService;
import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;
import com.example.backend.global.util.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @Test
    void 회원가입_성공() {
        // given
        SignupRequest request = new SignupRequest("user1", "1234", "nickname");
        given(userRepository.existsByUsername("user1")).willReturn(false);
        given(passwordEncoder.encode("1234")).willReturn("encodedPwd");

        User savedUser = User.builder()
                .username("user1")
                .password("encodedPwd")
                .nickname("nickname")
                .build();
        given(userRepository.save(any(User.class))).willReturn(savedUser);

        // when
        SignupResponse response = authService.signup(request);

        // then
        assertEquals("user1", response.getUsername());
        assertEquals("nickname", response.getNickname());
        assertEquals(List.of(UserRole.ROLE_USER), response.getRoles());
    }

    @Test
    void 중복_닉네임으로_회원가입_실패() {
        // given
        SignupRequest request = new SignupRequest("user1", "1234", "nickname");
        given(userRepository.existsByUsername("user1")).willReturn(true);

        // when & then
        CustomException ex = assertThrows(CustomException.class, () -> authService.signup(request));
        assertEquals(ErrorCode.USER_ALREADY_EXISTS, ex.getErrorCode());
    }
    @Test
    void 로그인_성공() {
        // given
        SigninRequest request = new SigninRequest("user1", "1234");

        User user = User.builder()
                .username("user1")
                .password("encodedPwd")
                .nickname("nickname")
                .build();
        ReflectionTestUtils.setField(user, "id", 1L);

        given(userService.getUserByUsername("user1")).willReturn(user);
        given(passwordEncoder.matches("1234", "encodedPwd")).willReturn(true);
        given(jwtUtil.createToken(eq(1L), eq("user1"), anySet(), eq("nickname")))
                .willReturn("Bearer test-token");

        // when
        SigninResponse response = authService.signin(request);

        // then
        assertEquals("Bearer test-token", response.getAccessToken());
    }

    @Test
    void 비밀번호_불일치로_로그인_실패() {
        // given
        SigninRequest request = new SigninRequest("user1", "wrongPwd");

        User user = User.builder()
                .username("user1")
                .password("encodedPwd")
                .nickname("nickname")
                .build();

        given(userService.getUserByUsername("user1")).willReturn(user);
        given(passwordEncoder.matches("wrongPwd", "encodedPwd")).willReturn(false);

        // when & then
        CustomException ex = assertThrows(CustomException.class, () -> authService.signin(request));
        assertEquals(ErrorCode.INVALID_CREDENTIALS, ex.getErrorCode());
    }
}
