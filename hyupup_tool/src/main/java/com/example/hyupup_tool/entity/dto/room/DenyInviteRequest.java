package com.example.hyupup_tool.entity.dto.room;

import lombok.NonNull;

public record DenyInviteRequest(
        @NonNull
        Long roomId
) {
}
