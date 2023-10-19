
public class CustomOauth2UserPrincipal implements OAuth2User {

    private Set<GrantedAuthority> authorities;

    private Map<String, Object> attributes;

    private String nameAttributeKey;
    private String id;

    public CustomOauth2UserPrincipal(Map<String, Object> attributes, String nameAttributeKey, String id) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.id = id;
    }


    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getName() {
        return nameAttributeKey;
    }


    public String getId() {
        return id;
    }
}
