
@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;

    private String id;

    private String platform;
    private String name;
    private String email;
    private String picture;

    private String nic;
    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String id, String platform, String name, String email, String picture, String nic) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.id = id;
        this.platform = platform;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.nic = nic;
    }

    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) { // OAuth2User에서 반환하는 사용자 정보는 Map이기 때문에 값 하나하나를 변환해줘야
        if("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        }

        return ofNaver(userNameAttributeName, attributes);
    }


    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .id((String) response.get("id"))
                .platform("NAVER")
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .nic((String) response.get("nickname"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Long id = (Long) attributes.get("id");
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuthAttributes.builder()
                .id(id.toString())
                .platform("KAKAO")
                .name((String) kakaoProfile.get("nickname"))
                .email((String) kakaoAccount.get("email"))
                .picture((String) kakaoProfile.get("profile_image_url"))
                .nic((String) kakaoProfile.get("nickname"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public UserAccount toEntity() {
        return UserAccount.builder()
                .socialId(id)
                .socialPlatform(platform)
                .email(email)
                .userNm(name)
                .profImg(picture)
                .nic(nic)
                .build();
    }

}
