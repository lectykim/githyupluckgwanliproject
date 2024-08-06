package com.example.hyupup_tool.entity.dto.room;

import lombok.NonNull;

public record InviteUserRequest (
        @NonNull
        Long userId,
        @NonNull
        Long roomId
){
}
