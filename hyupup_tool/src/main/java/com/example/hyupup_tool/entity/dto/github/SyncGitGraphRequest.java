package com.example.hyupup_tool.entity.dto.github;

import lombok.NonNull;

public record SyncGitGraphRequest(
        @NonNull
        Long roomId
) {
}
