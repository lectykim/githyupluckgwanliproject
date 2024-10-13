package com.example.hyupup_tool.entity.dto.room;

import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.dto.MemberToRoomDTO;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {

    private Long roomId;
    private Long maxMember;
    private String title;
    private List<MemberToRoomDTO> memberToRoomDTOList;
    private String repository;
    private String owner;
    private Long notReadMessageCount;
    private List<String> messageHistory;
}
