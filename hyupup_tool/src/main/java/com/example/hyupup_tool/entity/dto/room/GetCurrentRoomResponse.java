package com.example.hyupup_tool.entity.dto.room;

import java.util.List;

public record GetCurrentRoomResponse(
        List<RoomDTO> roomDTOList
) {
}
