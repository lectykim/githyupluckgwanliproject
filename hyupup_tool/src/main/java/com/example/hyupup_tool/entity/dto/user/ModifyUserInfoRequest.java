package com.example.hyupup_tool.entity.dto.user;

import lombok.NonNull;

public record ModifyUserInfoRequest(
        @NonNull
        Long userId
        ,
        @NonNull
        String email,
        @NonNull
        String pw,
        @NonNull
        String githubAccessToken
) {
}
