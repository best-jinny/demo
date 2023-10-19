@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserAccountRepository userAccountRepository;

    public UserAccountResponseDto getCurrentUser(CustomUserPrincipal userPrincipal) {

        UserAccount userAccount = userAccountRepository.findById(userPrincipal.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));

        return new UserAccountResponseDto(userAccount);

    }

}
