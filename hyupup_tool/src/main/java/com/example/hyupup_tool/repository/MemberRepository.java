package com.example.hyupup_tool.repository;

import com.example.hyupup_tool.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>,IMemberRepository {
    Optional<Member> findMemberByEmailAndPw(String email, String pw);

    Optional<Member> findMemberByEmail(String email);
    Boolean existsMemberByEmail(String email);

    @Override
    Optional<Member> findById(Long id);

    @Override
    Member save(Member member);

    @Override
    void delete(Member member);

    @Override
    Boolean existsByMemberId(Long memberId);
}
