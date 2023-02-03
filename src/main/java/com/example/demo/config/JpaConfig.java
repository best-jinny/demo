package com.example.demo.config;

import com.example.demo.dto.security.CustomUserPrincipal;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    /* Spring security 인증 후 로그인 객체 가져오는 방법 중 Bean 을 통해서 가져오기
    * Security Context Holder 를 통해 가져오는 방법
    * Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    * UserDetails userDetails = (UserDetails)principal;
    * String username = principal.getUsername();
    * String password = principal.getPassword();
    * */

    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .map(CustomUserPrincipal.class::cast)
                .map(CustomUserPrincipal::getUsername);
    }

}
