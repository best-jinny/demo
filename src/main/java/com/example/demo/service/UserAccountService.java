package com.example.demo.service;

import com.example.demo.domain.UserAccount;
import com.example.demo.dto.UserAccountDto;
import com.example.demo.exception.DuplicateMemberException;
import com.example.demo.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class UserAccountService {

    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder; // SecurityConfig 에서 Bean 으로 등록함

    @Transactional
    public UserAccountDto signup(UserAccountDto userAccountDto) {
        if (userAccountRepository.findById(userAccountDto.userId()).orElse(null) != null) {
            throw new DuplicateMemberException("이미 가입되어 있는 유저입니다.");
        }

        UserAccount newUser = UserAccount.of(userAccountDto.userId(), userAccountDto.userPassword(), userAccountDto.email(), userAccountDto.nickname(), userAccountDto.memo());
        newUser.encodePassword(passwordEncoder);
        return UserAccountDto.from(userAccountRepository.save(newUser));
    }



}
