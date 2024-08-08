package com.example.hyupup_tool.entity.dto.room;

import lombok.NonNull;

public record CreateRoomRequest (
        @NonNull
        Long memberId,
        @NonNull
        Long maxmember,
        @NonNull
        String title
){
}
