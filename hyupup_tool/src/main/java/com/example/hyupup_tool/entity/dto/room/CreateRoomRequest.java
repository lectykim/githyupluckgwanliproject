package com.example.hyupup_tool.entity.dto.room;

import lombok.NonNull;

public record CreateRoomRequest (
        @NonNull
        Long maxMember,
        @NonNull
        String title
){
}
