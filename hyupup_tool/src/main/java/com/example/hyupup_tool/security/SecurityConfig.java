package com.example.hyupup_tool.security;

import com.example.hyupup_tool.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MemberRepository memberRepository;
    private final CustomLoginSuccessHandler loginSuccessHandler;
    private final CustomLoginFailureHandler loginFailureHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHander;
    private final CustomAuthenticationEntryPointHandler authenticatinoEntryPointHandler;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public MvcRequestMatcher.Builder mvc (HandlerMappingIntrospector introspector){
        return new MvcRequestMatcher.Builder(introspector);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            HandlerMappingIntrospector introspector
    ) throws Exception{
        MvcRequestMatcher.Builder mvc = new MvcRequestMatcher.Builder(introspector);

        //white list (security 체크 항목 제외)
        MvcRequestMatcher []permitRequestMatcher ={
                mvc.pattern("/login-page"),
                mvc.pattern("/signup-page"),
                mvc.pattern("/api/v1/member-api/login"),
                mvc.pattern("/api/v1/member-api/signup"),
                mvc.pattern("/api/v1/member-api/can-signup-id"),
                mvc.pattern("/css/**"),
                mvc.pattern("/js/**"),
                mvc.pattern("/favicon.ico")
        };


        http
                .addFilterBefore(new CustomSecurityFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize)-> authorize
                        .requestMatchers(permitRequestMatcher).permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                //중복 로그인 방지
                .formLogin(login -> login
                        .loginProcessingUrl("/api/v1/member-api/login")
                        .successForwardUrl("/dashboard")
                        .successHandler(loginSuccessHandler)
                        .failureHandler(loginFailureHandler)
                )
                .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHander)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                //csrf 비활성화
                .csrf(AbstractHttpConfigurer::disable)
                //프레임 옵션 활성화 (h2 console 접근 위함)
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                //session management
                .sessionManagement(session-> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                //entry point exception handler
                .exceptionHandling(conf->conf
                        .authenticationEntryPoint(authenticatinoEntryPointHandler)
                        .accessDeniedHandler(accessDeniedHandler)
                );
        //build

        return http.build();
    }
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider(bCryptPasswordEncoder(),memberRepository);
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        CustomAuthenticationProvider provider = customAuthenticationProvider();
        return new ProviderManager(provider);
    }



}
