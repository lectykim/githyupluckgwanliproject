package com.example.hyupup_tool.entity.dto.room;

import lombok.NonNull;

public record InviteMemberRequest(
        @NonNull
        Long memberId,
        @NonNull
        Long roomId
){
}
