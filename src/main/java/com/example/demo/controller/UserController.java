package com.example.demo.controller;

import com.example.demo.domain.UserAccount;
import com.example.demo.dto.SignupDto;
import com.example.demo.dto.UserAccountDto;
import com.example.demo.dto.security.CustomUserPrincipal;
import com.example.demo.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserAccountService userAccountService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/signup")
    public ResponseEntity<UserAccountDto> signup(@RequestBody UserAccountDto userAccountDto) {
        return ResponseEntity.ok(userAccountService.signup(userAccountDto));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<String> getMyUserInfo(@AuthenticationPrincipal CustomUserPrincipal customUser) {
        return ResponseEntity.ok("관리자인증");
    }

    @GetMapping("/username")
    public ResponseEntity<String> currentUsername(Principal principal) {
        return ResponseEntity.ok(principal.getName());
    }

    @GetMapping("/userinfo")
    public ResponseEntity<CustomUserPrincipal> currentUsername(@AuthenticationPrincipal CustomUserPrincipal customUser) {
        return ResponseEntity.ok(customUser);
    }




}
