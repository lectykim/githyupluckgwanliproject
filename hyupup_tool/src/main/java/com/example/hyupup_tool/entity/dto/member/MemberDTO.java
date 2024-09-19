package com.example.hyupup_tool.entity.dto.member;

import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.util.AuthorityRole;
import com.example.hyupup_tool.util.PurchasePlan;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDTO {

    private Long memberId;
    private String email;
    private String pw;
    private PurchasePlan purchasePlan;
    private AuthorityRole authorityRole;
    private List<MemberToRoom> memberToRoomList;
    private String githubAccessToken;

}
