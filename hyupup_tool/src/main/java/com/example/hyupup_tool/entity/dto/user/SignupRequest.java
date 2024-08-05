package com.example.hyupup_tool.entity.dto.user;

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
