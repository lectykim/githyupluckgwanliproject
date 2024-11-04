package com.example.hyupup_tool.entity.dto.github;

import lombok.NonNull;

public record GetGitGraphRequest(
        @NonNull
        Long roomId
) {
}
