package com.example.hyupup_tool.security;

import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.security.auth.login.LoginException;
import java.util.Collections;

@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserDetailsService userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String memberEmail = authentication.getName();
        String password = (String)authentication.getCredentials();

        // UserDetails 를 가져오기 위해 UserDetailsService 구현 이용
        // 사용자가 존재하지 않으면
        //     loadUserByUsername() 는 AuthenticationException 예외 발생시킴
        //     인증 프로세스가 중단되고 HTTP 필터는 401 리턴
        UserDetails userDetails = userDetailsService.loadUserByUsername(memberEmail);

        //사용자가 존재하면 matches()로 암호 확인
        if(bCryptPasswordEncoder.matches(password,userDetails.getPassword())){
            return new UsernamePasswordAuthenticationToken(memberEmail,password,userDetails.getAuthorities());
        }else{
            throw new BadCredentialsException("BadCredentialsException...!!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // UsernamePasswordAuthenticationToken 는 Authentication 인터페이스의 한 구현이며,
        // 사용자 이름과 암호를 이용하는 표준 인증 요청을 나타냄
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}
