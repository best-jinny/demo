package com.example.demo.config;

import com.example.demo.security.handler.JwtAccessDeniedHandler;
import com.example.demo.security.handler.JwtAuthenticationEntryPoint;
import com.example.demo.security.jwt.JwtFilter;
import com.example.demo.security.jwt.JwtProvider;
import com.example.demo.security.AuthenticationProviderImpl;
import com.example.demo.security.CustomAuthenticationFilter;
import com.example.demo.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // 웹 보안 활성화. @AuthenticationPrincipal 애노테이션이 붙은 매개변수를 이용해 인증 처리
@EnableMethodSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final JwtProvider jwtProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final CustomUserDetailsService customUserDetailsService;

    // Spring security 5.7.1 부터 SecurityFilterChain 을 직접 Bean 으로 등록해서 사용하는 방식
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable() // REST api 서버에 인증정보 보관하지 않기 때문에 csrf 공격으로부터 (어느정도) 안전

                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션쿠키 방식의 인증 메카니즘 X
                .and()
                .formLogin().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) // 인증 안됐을 때
                .accessDeniedHandler(jwtAccessDeniedHandler) // 권한 없을 때

                .and()
                .authorizeHttpRequests()
                .antMatchers("/login", "/api/signup", "/auth/**", "/oauth2/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)

                 // oauth2 login 설정
                .oauth2Login()
                .successHandler(authenticationSuccessHandler)
                .redirectionEndpoint()
                .baseUri("/login/oauth2/code/**")
                .and()
                .userInfoEndpoint()
                .userService(oAuth2UserService);

            

        return httpSecurity.build();
    }

    @Bean
    public CustomAuthenticationFilter authenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());

        customAuthenticationFilter.setFilterProcessesUrl("/login");
        customAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler);
        customAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler);

        // BeanFactory에 의해 모든 property가 설정되고 난 뒤 실행
        //customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        AuthenticationProviderImpl provider = new AuthenticationProviderImpl(customUserDetailsService, passwordEncoder());
        return new ProviderManager(provider);
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter(jwtProvider);
    }

    @Bean // spring security 는 `{암호화 방식}암호화된 비밀번호` 형식의 패스워드 필요. There is no PasswordEncoder mapped for the id "null" 에러 발생
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Spring security + react cors 설정
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
        configuration.addAllowedHeader("*");
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
