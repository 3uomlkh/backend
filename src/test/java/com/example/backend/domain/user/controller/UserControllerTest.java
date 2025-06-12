package com.example.backend.domain.user.controller;

import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.global.util.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "jwt.secret.key=YWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWFhYWE="
})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private Long targetUserId;

    @BeforeEach
    void setup() {
        userRepository.deleteAll();

        User target = userRepository.save(User.builder()
                .username("target")
                .password("pw")
                .nickname("유저")
                .build());
        targetUserId = target.getId();
    }

    @Test
    void 관리자는_권한_부여_성공() throws Exception {
        User admin = userRepository.save(User.builder()
                .username("admin")
                .password("pw")
                .nickname("관리자")
                .build());
        admin.grantAdminRole();

        String token = jwtUtil.createToken(admin.getId(), admin.getUsername(), admin.getRoles(), admin.getNickname());

        mockMvc.perform(
                patch("/api/admin/users/{userId}/roles", targetUserId)
                        .header(HttpHeaders.AUTHORIZATION, token)
        ).andExpect(status().isOk());
    }

    @Test
    void 일반_사용자는_403() throws Exception {
        User user = userRepository.findByUsername("target").orElseThrow();

        String token = jwtUtil.createToken(user.getId(), user.getUsername(), user.getRoles(), user.getNickname());

        mockMvc.perform(
                patch("/api/admin/users/{userId}/roles", user.getId())
                        .header(HttpHeaders.AUTHORIZATION, token)
        ).andExpect(status().isForbidden());
    }
}