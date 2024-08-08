package com.example.hyupup_tool.entity.dto.member;

import lombok.NonNull;

public record LoginResponse(
        @NonNull
        Long memberId
) {
}
