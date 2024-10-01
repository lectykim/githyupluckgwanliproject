package com.example.hyupup_tool.entity.dto.room;

import java.util.List;

public record GetAllEventResponse(
        List<RoomDTO> inviteRoomDTOList
) {
}
