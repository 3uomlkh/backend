package com.example.backend.domain.user.service;

import com.example.backend.domain.user.dto.response.GrantAdminResponse;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.enums.UserRole;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.exception.CustomException;
import com.example.backend.global.exception.ErrorCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Test
    void 이름으로_유저_검색_성공() {
        // given
        String username = "user1";
        User user = User.builder()
                .username(username)
                .password("encodedPwd")
                .nickname("닉네임")
                .build();
        given(userRepository.findByUsername(username)).willReturn(Optional.of(user));

        // when
        User result = userService.getUserByUsername(username);

        // then
        assertEquals(username, result.getUsername());
        assertEquals("닉네임", result.getNickname());
    }

    @Test
    void 이름으로_유저_검색_실패() {
        // given
        String username = "unknown";
        given(userRepository.findByUsername(username)).willReturn(Optional.empty());

        // when & then
        CustomException ex = assertThrows(CustomException.class, () -> userService.getUserByUsername(username));
        assertEquals(ErrorCode.USER_NOT_FOUND, ex.getErrorCode());
    }

    @Test
    void 관리자_권한_부여_성공() {
        // given
        Long userId = 1L;
        User user = User.builder()
                .username("adminUser")
                .password("encodedPwd")
                .nickname("관리자")
                .build();

        given(userRepository.findById(userId)).willReturn(Optional.of(user));

        // when
        GrantAdminResponse response = userService.grantAdmin(userId);

        // then
        assertEquals("adminUser", response.getUsername());
        assertTrue(response.getRoles().contains(UserRole.ROLE_ADMIN));
    }

    @Test
    void 유저가_존재하지_않아_관리자_권한_부여_실패() {
        // given
        Long userId = 99L;
        given(userRepository.findById(userId)).willReturn(Optional.empty());

        // when & then
        CustomException ex = assertThrows(CustomException.class, () -> userService.grantAdmin(userId));
        assertEquals(ErrorCode.USER_NOT_FOUND, ex.getErrorCode());
    }
}
