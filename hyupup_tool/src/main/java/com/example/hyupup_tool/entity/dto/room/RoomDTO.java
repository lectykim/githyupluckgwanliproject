package com.example.hyupup_tool.entity.dto.room;

import com.example.hyupup_tool.entity.MemberToRoom;
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
    private List<MemberToRoom> memberToRoomList;
    private String repository;

}
