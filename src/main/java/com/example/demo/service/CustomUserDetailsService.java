package com.example.demo.service;

import com.example.demo.dto.security.CustomUserPrincipal;
import com.example.demo.dto.UserAccountDto;
import com.example.demo.repository.UserAccountRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/*
* Spring security 에서 유저 정보를 가져오는 `UserDetailsService` 인터페이스의 구현체
* */

@Component("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    private final UserAccountRepository userAccountRepository;

    public CustomUserDetailsService(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String username) { // DB에 접근해 사용자 정보를 가져오는 역할
        return userAccountRepository.findById(username)
                .map(UserAccountDto::from)
                .map(CustomUserPrincipal::from)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 데이터베이스에서 찾을 수 없습니다."));
    }
}