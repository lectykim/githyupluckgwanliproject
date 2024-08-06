package com.example.hyupup_tool.entity.dto.room;

import lombok.NonNull;

public record ChangeMasterRequest(
        @NonNull
        Long roomId,
        @NonNull
        Long oldMasterId,
        @NonNull
        Long newMasterId
) {
}
