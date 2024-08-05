package com.example.hyupup_tool.entity.dto.user;

import lombok.NonNull;

public record LoginResponse(
        @NonNull
        Long userId
) {
}
