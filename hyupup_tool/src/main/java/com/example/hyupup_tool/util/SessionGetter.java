package com.example.hyupup_tool.util;

import com.example.hyupup_tool.entity.dto.member.GetMemberInfoResponse;
import com.example.hyupup_tool.entity.dto.member.MemberDTO;
import com.example.hyupup_tool.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SessionGetter {
    public static MemberDTO getCurrentMemberDto(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        return userDetails.getMember().toDto();
    }
}
