package com.example.hyupup_tool.entity.dto.room;

import lombok.NonNull;

public record AcceptInviteRequest(
        @NonNull
        Long roomId
) {
}
