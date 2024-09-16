package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.dto.member.*;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.exception.client.UnauthorizedException;
import com.example.hyupup_tool.exception.server.ServerException;
import com.example.hyupup_tool.repository.MemberRepository;
import com.example.hyupup_tool.util.AuthorityRole;
import com.example.hyupup_tool.validator.MemberValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberValidator memberValidator;
    private final PasswordEncoder passwordEncoder;
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
                AuthorityRole.NORMAL_MEMBER
        );

        var insertedmember = memberRepository.save(member);

        return new SignupResponse(insertedmember.getMemberId());


    }

    public CanSignupIdResponse canSignupId(CanSignupIdRequest request){
        memberValidator.canSignupValidator(request);
        return new CanSignupIdResponse(!memberValidator.canSignup(request.email()));
    }

    @Transactional
    public ModifyMemberInfoResponse modifyMemberInfo(ModifyMemberInfoRequest request) {
        memberValidator.modifyMemberInfoRequestValidator(request);
        var user = memberRepository.findById(request.memberId())
                .orElseThrow(()-> new BadRequestException("Id is not found"));
            try {
                var updatedUser = memberRepository.save(Member.of(request.email(),
                        request.pw(),
                        request.githubAccessToken(),
                        AuthorityRole.NORMAL_MEMBER)
                );
                return new ModifyMemberInfoResponse(updatedUser.getMemberId());
            } catch (DataIntegrityViolationException e){
                throw new ServerException(e.getMessage());
            }
    }
}
