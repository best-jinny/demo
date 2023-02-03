package com.example.demo.controller.rest;

import com.example.demo.domain.UserAccount;
import com.example.demo.dto.SignupDto;
import com.example.demo.dto.UserAccountDto;
import com.example.demo.service.UserAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class UserController {

    private final UserAccountService userAccountService;

    @PostMapping("/signup")
    public ResponseEntity<SignupDto> signup(@RequestBody SignupDto signupDto) {
        return ResponseEntity.ok(userAccountService.signup(signupDto));
    }

}
