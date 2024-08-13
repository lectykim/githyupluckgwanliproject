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
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<Member> memberOptional = memberRepository.findMemberByEmailAndPw(loginRequest.email(),loginRequest.pw());

        if(memberOptional.isEmpty()){
            throw new UnauthorizedException("Email and Password not correct");
        }

        return new LoginResponse(memberOptional.get().getMemberId());
    }

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {
        if(memberValidator.canSignup(signupRequest.email())){
            throw new BadRequestException("Already signup email");
        }

        Member member = Member.of(signupRequest.email(),
                passwordEncoder.encode(signupRequest.pw()),
                signupRequest.githubAccessToken(),
                AuthorityRole.NORMAL_MEMBER
        );

        var insertedmember = memberRepository.save(member);

        return new SignupResponse(insertedmember.getMemberId());


    }

    public CanSignupIdResponse canSignupId(String email){
        return new CanSignupIdResponse(!memberValidator.canSignup(email));
    }

    @Transactional
    public ModifyMemberInfoResponse modifyMemberInfo(ModifyMemberInfoRequest request) {
        var user = memberRepository.findById(request.memberId());
        if(user.isPresent()){
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


        }else{
            throw new ServerException("User is not exist.");
        }
    }
}
