package com.example.hyupup_tool.entity.dto.room;

import lombok.NonNull;

public record UpdateRoomRequest (
        @NonNull
        Long roomId,
        @NonNull
        String title,
        @NonNull
        Long maxMember
){
}
