//package com.example.demo.security;
//
//import com.example.demo.service.CustomUserDetailsService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.stereotype.Component;
//
///*
// * AuthenticationManager 하위 실제 인증 처리하는 AuthenticationProvider 구현
// */
//
//
//@Component
//@RequiredArgsConstructor
//public class AuthenticationProviderImpl implements AuthenticationProvider {
//
//    private final CustomUserDetailsService customUserDetailsService;
//
//    // CustomAuthenticationFilter 에서 생성된 UsernamePasswordAuthenticationToken 으로 해당 회원 DB 조회하여 인증
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
//
//        String username = token.getName();
//        String password = (String) token.getCredentials();
//
//        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
//
//
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
//}
