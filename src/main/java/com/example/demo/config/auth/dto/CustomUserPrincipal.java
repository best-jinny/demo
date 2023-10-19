/*
 * Spring Security 에서 사용자 정보를 불러오기 위해서 구현해야하는 인터페이스 `UserDetails` 의 구현체
 * Security 에서 다루는 유저 정보(UserDetails)와 실제 Domain Entity 사이에서 차이가 있을 수 있기 때문에
 * 따로 필요한 데이터만을 이용해서 UserDetails 객체를 생성한다.
 * */
@NoArgsConstructor
public class CustomUserPrincipal implements UserDetails {

    private Long userId;
    private String email;
    private String userNm;
    private String nic;


    public CustomUserPrincipal(Long userId, String email, String userNm, String nic) {
        this.userId = userId;
        this.email = email;
        this.userNm = userNm;
        this.nic = nic;
    }

    @Builder
    public CustomUserPrincipal(UserAccountResponseDto userAccountResponseDto) {
        this.userId = userAccountResponseDto.getUserId();
        this.nic = userAccountResponseDto.getNic();

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return userNm;
    }

    @Override
    public boolean isAccountNonExpired() { // 만료 안됨
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { // 잠기지 않음
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { // 비밀번호 만료 안됨
        return true;
    }

    @Override
    public boolean isEnabled() { // 활성화
        return true;
    }

    public Long getUserId() {
        return userId;
    }



}
