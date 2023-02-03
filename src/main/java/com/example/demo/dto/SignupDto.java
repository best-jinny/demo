package com.example.demo.dto;

import com.example.demo.domain.UserAccount;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {

    private String userId;
    private String userPassword;
    private String email;
    private String nickname;
    private String memo;


    public static SignupDto of(String userId, String userPassword, String email, String nickname, String memo) {
        return new SignupDto(userId, userPassword, email, nickname, memo);
    }

    public static SignupDto from(UserAccount entity){
        return new SignupDto(
                entity.getUserId(),
                entity.getUserPassword(),
                entity.getEmail(),
                entity.getNickname(),
                entity.getMemo()
        );
    }

    public UserAccount toEntity() {
        return UserAccount.of(
                userId,
                userPassword,
                email,
                nickname,
                memo
        );
    }



}
