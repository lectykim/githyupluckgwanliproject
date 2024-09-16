package com.example.hyupup_tool.validator;

import com.example.hyupup_tool.entity.dto.member.CanSignupIdRequest;
import com.example.hyupup_tool.entity.dto.member.LoginRequest;
import com.example.hyupup_tool.entity.dto.member.ModifyMemberInfoRequest;
import com.example.hyupup_tool.entity.dto.member.SignupRequest;

public interface MemberValidator {

    boolean canSignup(String email);


    void signupValidator(SignupRequest request);

    void canSignupValidator(CanSignupIdRequest request);

    void loginRequestValidator(LoginRequest request);

    void modifyMemberInfoRequestValidator(ModifyMemberInfoRequest request);



}
