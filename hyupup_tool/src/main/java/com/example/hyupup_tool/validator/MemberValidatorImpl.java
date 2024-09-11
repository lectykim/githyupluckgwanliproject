package com.example.hyupup_tool.validator;

import com.example.hyupup_tool.repository.IMemberRepository;
import com.example.hyupup_tool.repository.MemberRepository;
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
}
