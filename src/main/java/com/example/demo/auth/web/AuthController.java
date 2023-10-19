@RequiredArgsConstructor
@RequestMapping("auth")
@RestController
public class AuthController {

    private final AuthService authService;

    @GetMapping("/")
    public UserAccountResponseDto getCurrentUser(@AuthenticationPrincipal CustomUserPrincipal customUserPrincipal) {   //@AuthenticationPrincipal : 인증된 유저 정보 가져옴
        return authService.getCurrentUser(customUserPrincipal);
    }


}
