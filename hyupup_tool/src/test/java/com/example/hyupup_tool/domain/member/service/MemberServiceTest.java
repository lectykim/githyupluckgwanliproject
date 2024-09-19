package com.example.hyupup_tool.domain.member.service;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.fake.FakeDaoManager;
import com.example.hyupup_tool.fake.FakeDomainInterfaceFactory;
import com.example.hyupup_tool.repository.IMemberRepository;
import com.example.hyupup_tool.util.AuthorityRole;
import com.example.hyupup_tool.validator.MemberValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

public class MemberServiceTest {

    private final IMemberRepository memberRepository = FakeDaoManager.getIMemberRepository();
    private final MemberValidator memberValidator = FakeDomainInterfaceFactory.getFakeMemberValidator();
    @BeforeEach
    void clearAll(){
        FakeDaoManager.clearAll();
    }

    private static final String MEMBER_EMAIL = "email@email.com";

    private static final String MEMBER_PW = "password";

    private static final String GITHUB_ACCESS_TOKEN = "github_access_token";


    private static final AuthorityRole AUTHORITY_ROLE = AuthorityRole.ROLE_NORMAL_MEMBER;
    @Test
    @DisplayName("성공 : 회원가입")
    void success_signup_01(){
        //Validate ID
        assertFalse(memberValidator.canSignup(MEMBER_EMAIL));

        //Make Member Entity
        var memberEntity = Member.of(MEMBER_EMAIL,MEMBER_PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE);

        var savedMemberEntity = memberRepository.save(memberEntity);

        assertNotNull(savedMemberEntity);
        assertEquals(savedMemberEntity.getMemberId(),1);


    }

    void signup_user(){
        var memberEntity = Member.of(MEMBER_EMAIL,MEMBER_PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE);
        var savedMemberEntity = memberRepository.save(memberEntity);
    }

    @Test
    @DisplayName("성공 : 로그인")
    void success_login_01(){
        signup_user();

        var memberEntity = memberRepository.findMemberByEmailAndPw(MEMBER_EMAIL,MEMBER_PW);
        assertNotNull(memberEntity.get());
        assertEquals(memberEntity.get().getMemberId(),1);

    }

    @Test
    @DisplayName("성공 : 회원가입 시 중복된 아이디 검출")
    void success_canSignup_01(){
        signup_user();

        assertFalse(memberValidator.canSignup(MEMBER_EMAIL));
    }

    @Test
    @DisplayName("성공 : 유저 정보 변경")
    void success_modifyUserInfo_01(){
        signup_user();

        var memberEntity = memberRepository.findById(1L);
        memberEntity.get().setEmail("NEW_EMAIL");
        memberEntity.get().setPw("newPw");
        var newMemberEntity = memberRepository.save(memberEntity.get());
        assertEquals(newMemberEntity.getEmail(),"NEW_EMAIL");
        assertEquals(newMemberEntity.getPw(),"newPw");
        assertEquals(newMemberEntity.getMemberId(),1L);
    }




}
