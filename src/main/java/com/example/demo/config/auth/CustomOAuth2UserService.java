/** 소셜 로그인 이후 가져온 사용자 정보를 기반으로 가입 및 정보 수정 등의 기능 지원 */
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserAccountRepository userAccountRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // 현재 로그인 진행 중인 서비스를 구분하는 코드
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails() // OAuth2로그인 진행 시 키가 되는 필드 값.(primary key)
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttributeName, oAuth2User.getAttributes());

        UserAccount user = saveOrUpdate(attributes);

        //return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes.getAttributes(), attributes.getNameAttributeKey());

        return new CustomOauth2UserPrincipal(attributes.getAttributes(), attributes.getNameAttributeKey(), attributes.getId());

    }

    private UserAccount saveOrUpdate(OAuthAttributes attributes) {
        UserAccount user = userAccountRepository.findBySocialId(attributes.getId())
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                .orElse(attributes.toEntity());

        return userAccountRepository.save(user);
    }
}
