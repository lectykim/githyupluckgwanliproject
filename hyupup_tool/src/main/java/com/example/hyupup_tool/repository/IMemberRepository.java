package com.example.hyupup_tool.repository;

import com.example.hyupup_tool.entity.Member;

import java.util.Optional;

public interface IMemberRepository {
    Optional<Member> findMemberByEmailAndPw(String email, String pw);

    Optional<Member> findMemberByEmail(String email);
    Boolean existsMemberByEmail(String email);

    Optional<Member> findById(Long id);

    Member save(Member member);

    void delete(Member member);

    Boolean existsByMemberId(Long memberId);
}
