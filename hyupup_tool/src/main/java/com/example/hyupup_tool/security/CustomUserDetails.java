package com.example.hyupup_tool.security;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.util.AuthorityRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final Member member;
    private final String email;
    private final String pw;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        AuthorityRole authorityRole = member.getAuthorityRole();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authorityRole.name());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return pw;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
