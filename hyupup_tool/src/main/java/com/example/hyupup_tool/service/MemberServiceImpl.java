package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.dto.member.*;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.exception.client.UnauthorizedException;
import com.example.hyupup_tool.exception.server.ServerException;
import com.example.hyupup_tool.repository.MemberRepository;
import com.example.hyupup_tool.security.CustomUserDetails;
import com.example.hyupup_tool.util.AuthorityRole;
import com.example.hyupup_tool.util.SessionGetter;
import com.example.hyupup_tool.validator.MemberValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;
    private final PasswordEncoder passwordEncoder;
    private final SessionGetter sessionGetter;
    public LoginResponse login(LoginRequest request) {
        memberValidator.loginRequestValidator(request);
        Optional<Member> memberOptional = memberRepository.findMemberByEmailAndPw(request.email(),request.pw());

        if(memberOptional.isEmpty()){
            throw new UnauthorizedException("Email and Password not correct");
        }

        return new LoginResponse(memberOptional.get().getMemberId());
    }

    @Transactional
    public SignupResponse signup(SignupRequest request) {
        memberValidator.signupValidator(request);
        if(memberValidator.canSignup(request.email())){
            throw new BadRequestException("Already signup email");
        }

        Member member = Member.of(request.email(),
                passwordEncoder.encode(request.pw()),
                request.githubAccessToken(),
                AuthorityRole.ROLE_NORMAL_MEMBER,
                request.nickname()
        );

        var insertedmember = memberRepository.save(member);
        //sessionGetter.resetCurrentMemberDto();
        return new SignupResponse(insertedmember.getMemberId());


    }

    public CanSignupIdResponse canSignupId(CanSignupIdRequest request){
        memberValidator.canSignupValidator(request);
        return new CanSignupIdResponse(!memberValidator.canSignup(request.email()));
    }

    @Transactional
    public ModifyMemberInfoResponse modifyMemberInfo(ModifyMemberInfoRequest request) {
        memberValidator.modifyMemberInfoRequestValidator(request);
        var memberDto = SessionGetter.getCurrentMemberDto();
        var newMemberEntity = memberRepository.findById(memberDto.getMemberId())
                .orElseThrow(()->new BadRequestException("Session Authentication Error"));
        newMemberEntity.setNickname(request.nickname());
        if(!Objects.equals(newMemberEntity.getPw(), request.pw())){
            newMemberEntity.setPw(passwordEncoder.encode(request.pw()));
        }
        sessionGetter.resetCurrentMemberDto();
        newMemberEntity.setGithubAccessToken(request.githubAccessToken());
        memberRepository.save(newMemberEntity);

        return new ModifyMemberInfoResponse(newMemberEntity.getMemberId());
    }

    @Override
    public GetMemberInfoResponse getMemberInfo(GetMemberInfoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        return new GetMemberInfoResponse(userDetails.getMember().toDto());
    }
}
