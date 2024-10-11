package com.example.hyupup_tool.entity.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberToRoomDTO {
    private Long memberId;
    private Long roomId;
    private boolean isMaster;

}
