package com.example.hyupup_tool.util;

import com.example.hyupup_tool.entity.dto.member.GetMemberInfoResponse;
import com.example.hyupup_tool.entity.dto.member.MemberDTO;
import com.example.hyupup_tool.security.CustomUserDetails;
import com.example.hyupup_tool.security.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class SessionGetter {

    private final CustomUserDetailsService customUserDetailsService;

    public static MemberDTO getCurrentMemberDto(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        return userDetails.getMember().toDto();
    }

    public void resetCurrentMemberDto(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        SecurityContextHolder.getContext().setAuthentication(createNewAuthentication(authentication,userDetails.getUsername()));
    }

    protected Authentication createNewAuthentication (Authentication currentAuthentication, String email){
        UserDetails newPrincipal = customUserDetailsService.loadUserByUsername(email);
        UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(newPrincipal,currentAuthentication.getCredentials(),newPrincipal.getAuthorities());
        newToken.setDetails(newPrincipal);
        return newToken;
    }
}
