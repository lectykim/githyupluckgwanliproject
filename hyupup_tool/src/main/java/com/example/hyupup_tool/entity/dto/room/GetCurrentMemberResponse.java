package com.example.hyupup_tool.entity.dto.room;

import com.example.hyupup_tool.entity.dto.member.MemberDTO;

import java.util.List;

public record GetCurrentMemberResponse(
        List<MemberDTO> memberDTOList
) {
}
