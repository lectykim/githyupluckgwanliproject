package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.member.*;
import org.springframework.stereotype.Service;

@Service
public interface MemberService {
    LoginResponse login(LoginRequest request);
    SignupResponse signup(SignupRequest request);
    CanSignupIdResponse canSignupId(CanSignupIdRequest request);
    ModifyMemberInfoResponse modifyMemberInfo(ModifyMemberInfoRequest request);

    GetMemberInfoResponse getMemberInfo(GetMemberInfoRequest request);
}
