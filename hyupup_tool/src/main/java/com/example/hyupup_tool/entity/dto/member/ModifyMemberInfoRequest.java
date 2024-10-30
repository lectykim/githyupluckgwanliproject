package com.example.hyupup_tool.entity.dto.member;

import lombok.NonNull;

public record ModifyMemberInfoRequest(
        @NonNull
        String pw,
        @NonNull
        String githubAccessToken,
        @NonNull
        String nickname
) {
}
