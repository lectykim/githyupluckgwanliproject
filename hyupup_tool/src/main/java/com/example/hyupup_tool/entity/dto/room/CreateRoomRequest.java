package com.example.hyupup_tool.entity.dto.room;

import lombok.NonNull;

public record CreateRoomRequest (
        @NonNull
        Long userId,
        @NonNull
        Long maxUser,
        @NonNull
        String title
){
}
