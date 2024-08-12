package com.example.hyupup_tool.security;

import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var member = memberRepository.findMemberByEmail(username)
                .orElseThrow(()-> new BadRequestException("Can't find member"));
        return new CustomUserDetails(member,member.getEmail(),member.getPw());

    }
}
