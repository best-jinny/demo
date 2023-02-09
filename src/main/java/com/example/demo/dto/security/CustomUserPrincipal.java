package com.example.demo.dto.security;

import com.example.demo.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/*
* Spring Security 에서 사용자 정보를 불러오기 위해서 구현해야하는 인터페이스 `UserDetails` 의 구현체
* Security 에서 다루는 유저 정보(UserDetails)와 실제 Domain Entity 사이에서 차이가 있을 수 있기 때문에
* 따로 필요한 데이터만을 이용해서 UserDetails 객체를 생성한다.
* */
public record CustomUserPrincipal(
        String username, // 계정의 고유한 값
        String password,
        Collection<? extends GrantedAuthority> authorities, // 해당 유저의 권한 목록
        String email,
        String nickname,
        String memo
) implements UserDetails {

    public static CustomUserPrincipal of (String username, String password, String email, String nickname, String memo) {

        Set<RoleType> roleType = Set.of(RoleType.USER);
        return new CustomUserPrincipal(
                username,
                password,
                roleType.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
                ,
                email,
                nickname,
                memo
        );
    }

    public static CustomUserPrincipal from(UserAccountDto dto) {
        return CustomUserPrincipal.of(
                dto.userId(),
                dto.userPassword(),
                dto.email(),
                dto.nickname(),
                dto.memo()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                username,
                password,
                email,
                nickname,
                memo
        );
    }

    @Override public String getUsername() { return username; }

    @Override public String getPassword() { return password; }

    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override public boolean isAccountNonExpired() { return true; } // true : 만료 안됨
    @Override public boolean isAccountNonLocked() { return true; } // true : 잠기지 않음
    @Override public boolean isCredentialsNonExpired() { return true; } // true : 비밀번호 만료 안됨
    @Override public boolean isEnabled() { return true; } // true : 활성화

    public enum RoleType {
        USER("ROLE_USER");

        @Getter
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}