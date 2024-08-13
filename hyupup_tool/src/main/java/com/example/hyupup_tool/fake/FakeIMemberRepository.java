package com.example.hyupup_tool.fake;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.repository.IMemberRepository;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeIMemberRepository implements IMemberRepository {

    @Getter
    Map<Long,Member> db = new HashMap<>();

    AtomicLong idGenerator = new AtomicLong();

    @Override
    public Optional<Member> findMemberByEmailAndPw(String email, String pw) {
        return null;
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Boolean existsMemberByEmail(String email) {
        return null;
    }

    @Override
    public Optional<Member> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Member save(Member member) {
        return null;
    }

    @Override
    public void delete(Member member) {

    }
}
