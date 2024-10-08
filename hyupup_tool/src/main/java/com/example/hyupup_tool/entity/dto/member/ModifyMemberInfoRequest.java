package com.example.hyupup_tool.entity.dto.member;

import lombok.NonNull;

public record ModifyMemberInfoRequest(
        @NonNull
        Long memberId
        ,
        @NonNull
        String email,
        @NonNull
        String pw,
        @NonNull
        String githubAccessToken,
        @NonNull
        String nickname
) {
}
