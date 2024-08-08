package com.example.hyupup_tool.entity.dto.member;

import lombok.NonNull;

public record SignupRequest(
        @NonNull
        String email,
        @NonNull
        String pw,
        @NonNull
        String githubAccessToken
) {
}
