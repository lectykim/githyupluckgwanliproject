package com.example.hyupup_tool.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class CustomSecurityFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CachingRequestWrapper requestWrapper = new CachingRequestWrapper(request);
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> jsonMap = mapper.readValue(requestWrapper.getInputStream(),Map.class);
        String username = (String)jsonMap.get("email");
        String pw = (String)jsonMap.get("pw");
        String url = request.getRequestURI();

        if(username != null && pw != null && url.equals("/api/v1/member-api/login")){
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if(!passwordEncoder.matches(pw,userDetails.getPassword())){
                throw new IllegalAccessError("password not corrected");
            }
            SecurityContext context = SecurityContextHolder.createEmptyContext();
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            context.setAuthentication(authentication);

            SecurityContextHolder.setContext(context);
        }
        filterChain.doFilter(requestWrapper,response);
    }
}
