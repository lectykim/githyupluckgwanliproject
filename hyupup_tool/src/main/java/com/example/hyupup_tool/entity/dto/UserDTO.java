package com.example.hyupup_tool.entity.dto;

import com.example.hyupup_tool.entity.User;
import com.example.hyupup_tool.util.PurchasePlan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long userId;
    private String email;;
    private String pw;
    private String githubAccessToken;
    private PurchasePlan purchasePlan;


    public User toUserEntity(){
        return User.builder()
                .userId(userId)
                .email(email)
                .pw(pw)
                .githubAccessToken(githubAccessToken)
                .purchasePlan(purchasePlan)
                .build();
    }
}
