package com.example.demo.security.handler;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint { // 인증되지 않은 사용자의 리소스 접근 처리

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        String exception = (String)request.getAttribute("exception");

        if(exception != null) {
            response.getWriter().write("invalid JWT TOKEN");
        }
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized Error

    }
}
