package com.example.hyupup_tool.fake;

import com.example.hyupup_tool.repository.MemberRepository;
import com.example.hyupup_tool.validator.MemberValidator;
import com.example.hyupup_tool.validator.MemberValidatorImpl;
import lombok.Getter;

public class FakeDomainInterfaceFactory {
    private FakeDomainInterfaceFactory(){

    }

    @Getter
    private static final MemberValidator fakeMemberValidator = new MemberValidatorImpl(
            FakeDaoManager.getIMemberRepository()
    );

}
