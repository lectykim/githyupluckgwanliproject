package com.example.hyupup_tool.entity.dto;

import com.example.hyupup_tool.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDTO {
    private String email;
    private String pw;
    private String githubAccessToken;

    public User toUserEntity(){
        return User.builder()
                .email(email)
                .pw(pw)
                .githubAccessToken(githubAccessToken)
                .build();
    }

}
