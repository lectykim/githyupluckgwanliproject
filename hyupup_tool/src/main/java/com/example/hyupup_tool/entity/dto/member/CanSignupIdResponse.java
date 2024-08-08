package com.example.hyupup_tool.entity.dto.member;

import lombok.NonNull;

public record CanSignupIdResponse(
        @NonNull
        Boolean canSignup
) {
}
