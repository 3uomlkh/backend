package com.example.backend.domain.auth.service;

import com.example.backend.domain.auth.dto.request.SigninRequest;
import com.example.backend.domain.auth.dto.request.SignupRequest;
import com.example.backend.domain.auth.dto.response.SigninResponse;
import com.example.backend.domain.auth.dto.response.SignupResponse;
import com.example.backend.domain.user.entity.User;
import com.example.backend.domain.user.repository.UserRepository;
import com.example.backend.domain.user.service.UserService;
import com.example.backend.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalStateException("이미 존재하는 이름입니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .build();
        User savedUser = userRepository.save(user);

        return SignupResponse.from(savedUser);
    }

    public SigninResponse signin(SigninRequest request) {
        User user = userService.getUserByUsername(request.getUsername());

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }

        String token = jwtUtil.createToken(user.getId(), user.getUsername(), user.getRoles(), user.getNickname());
        return new SigninResponse(token);
    }
}