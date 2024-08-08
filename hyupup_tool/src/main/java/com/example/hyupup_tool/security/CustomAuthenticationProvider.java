package com.example.hyupup_tool.security;

import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.security.auth.login.LoginException;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String memberEmail = authentication.getName();
        String password = (String)authentication.getCredentials();


        try{
            var memberInfo = memberRepository.findMemberByEmail(memberEmail);
            if(memberInfo.isEmpty()){
                throw new LoginException("Member not found");
            }
            if(!bCryptPasswordEncoder.matches(password,memberInfo.get().getPw())){
                throw new LoginException("Password not matched");
            }

            return new UsernamePasswordAuthenticationToken(
                    memberInfo.get().getEmail(),
                    memberInfo.get().getPw(),
                    Collections.singleton(new SimpleGrantedAuthority(memberInfo.get().getAuthorityRole().name()))
            );
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }


}
