package com.example.demo.security.handler;

import com.example.demo.jwt.JwtFilter;
import com.example.demo.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/* 인증 성공 */

@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 전달 받은 인증 정보를 SecurityContextHolder 에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 토큰 발급
        final String token = jwtProvider.createToken(authentication);

        // Response
        response.addHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + token);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
