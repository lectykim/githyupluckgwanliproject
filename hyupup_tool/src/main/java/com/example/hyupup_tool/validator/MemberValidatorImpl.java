package com.example.hyupup_tool.validator;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.dto.member.CanSignupIdRequest;
import com.example.hyupup_tool.entity.dto.member.LoginRequest;
import com.example.hyupup_tool.entity.dto.member.ModifyMemberInfoRequest;
import com.example.hyupup_tool.entity.dto.member.SignupRequest;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.repository.IMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidatorImpl implements MemberValidator{

    private final IMemberRepository memberRepository;

    @Override
    public boolean canSignup(String email) {
        return memberRepository.existsMemberByEmail(email);
    }


    @Override
    public void signupValidator(SignupRequest request) {
        Member.validateEmail(request.email());
        Member.validatePw(request.pw());
        Member.validateGithubAccessToken(request.githubAccessToken());
    }

    @Override
    public void canSignupValidator(CanSignupIdRequest request) {
        Member.validateEmail(request.email());
    }

    @Override
    public void loginRequestValidator(LoginRequest request) {
        Member.validateEmail(request.email());
        Member.validatePw(request.pw());
    }

    @Override
    public void modifyMemberInfoRequestValidator(ModifyMemberInfoRequest request) {
        //Member.validateEmail(request.email());
        Member.validatePw(request.pw());
        Member.validateGithubAccessToken(request.githubAccessToken());
    }


}
