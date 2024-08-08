package com.example.hyupup_tool.security;

import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.repository.MemberRepository;
import com.example.hyupup_tool.util.AuthorityRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var memberInfo = memberRepository.findMemberByEmail(email)
                .orElseThrow(()->new BadRequestException("Can't find member"));
        return new User(memberInfo.getEmail(),memberInfo.getPw(),getAuthority(memberInfo.getAuthorityRole()));

    }

    private Collection<? extends GrantedAuthority> getAuthority(AuthorityRole role){
        return AuthorityUtils.createAuthorityList(role.name());
    }
}
