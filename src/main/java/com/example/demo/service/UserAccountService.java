package com.example.demo.service;

import com.example.demo.dto.SignupDto;
import com.example.demo.exception.DuplicateMemberException;
import com.example.demo.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder; // SecurityConfig 에서 Bean 으로 등록함

    @Transactional
    public SignupDto signup(SignupDto signupDto) {
        if (userAccountRepository.findById(signupDto.getUserId()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        SignupDto user = SignupDto.builder()
                .userId(signupDto.getUserId())
                .userPassword(passwordEncoder.encode(signupDto.getUserPassword()))
                .email(signupDto.getEmail())
                .nickname(signupDto.getNickname())
                .build();

        return SignupDto.from(userAccountRepository.save(user.toEntity()));
    }



}
