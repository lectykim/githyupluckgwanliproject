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
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter;
import org.springframework.security.web.session.SessionManagementFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final CustomLoginSuccessHandler loginSuccessHandler;
    private final CustomLoginFailureHandler loginFailureHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final CustomAuthenticationEntryPointHandler authenticationEntryPointHandler;
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
                mvc.pattern("/"),
                mvc.pattern("/signup-page"),
                mvc.pattern("/api/v1/member-api/login"),
                mvc.pattern("/error"),
                mvc.pattern("/api/v1/member-api/signup"),
                mvc.pattern("/api/v1/member-api/can-signup-id"),
                mvc.pattern("/css/**"),
                mvc.pattern("/js/**"),
                mvc.pattern("/favicon.ico")
        };



        http
                .addFilterBefore(getCustomFilter(), UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests((authorize)-> authorize
                        .requestMatchers(permitRequestMatcher).permitAll()
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest()
                        .authenticated()
                )
                //중복 로그인 방지
                .formLogin(login -> login
                        .loginPage("/login-page")
                        .loginProcessingUrl("/api/v1/member-api/login")

                )
                .logout(logout->logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(logoutSuccessHandler)
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
                        .authenticationEntryPoint(authenticationEntryPointHandler)
                        .accessDeniedHandler(accessDeniedHandler)
                );
        //build

        return http.build();
    }
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(){
        return new CustomAuthenticationProvider(bCryptPasswordEncoder(),userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        CustomAuthenticationProvider provider = customAuthenticationProvider();
        return new ProviderManager(provider);
    }


    /*
    * Custom Filter 만들 때에는
    * 그 Filter가 어떤 일을 하는지에 대해
    * 정확하게 분석하고 만들어야 한다.
    * 이번 경우에는 UsernamePasswordAuthenticationFilter 대신 CustomSecurityFilter를 사용하였지만
    * CustomSecurityFilter가 가르키는 successhandler는 기본 successhandler이기 때문에
    * index로 redirecting 되었던 것
    * */
    protected UsernamePasswordAuthenticationFilter getCustomFilter(){
        CustomSecurityFilter customSecurityFilter =new CustomSecurityFilter(authenticationManager());
        customSecurityFilter.setAuthenticationSuccessHandler(loginSuccessHandler);
        customSecurityFilter.setAuthenticationFailureHandler(loginFailureHandler);
        return customSecurityFilter;
    }



}
