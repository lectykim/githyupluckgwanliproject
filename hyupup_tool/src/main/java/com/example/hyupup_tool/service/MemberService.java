package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.member.*;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    LoginResponse login(LoginRequest loginRequest);
    SignupResponse signup(SignupRequest signupRequest);
    CanSignupIdResponse canSignupId(String email);
    ModifyMemberInfoResponse modifyMemberInfo(ModifyMemberInfoRequest request);
}
