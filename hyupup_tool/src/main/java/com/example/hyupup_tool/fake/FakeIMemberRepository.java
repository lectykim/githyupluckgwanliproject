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
    public Optional<Member> findById(Long id) {
        return db.get(id) == null ? Optional.empty() : Optional.of(db.get(id));
    }

    @Override
    public Member save(Member member) {
        if(member.getMemberId() != null){
            db.put(member.getMemberId(),member);
            return member;
        }
        var newId = idGenerator.addAndGet(1);
        FakeSetter.setField(member,"memberId",newId);

        db.put(newId,member);
        return member;
    }

    @Override
    public void delete(Member member) {
        db.remove(member.getMemberId());
    }

    @Override
    public Optional<Member> findMemberByEmailAndPw(String email, String pw) {
        for(Long id:db.keySet()){
            if(db.get(id).getEmail().equals(email) && db.get(id).getPw().equals(pw)){
                return Optional.of(db.get(id));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        for(Long id:db.keySet()){
            if(db.get(id).getEmail().equals(email)){
                return Optional.of(db.get(id));
            }
        }
        return Optional.empty();
    }

    @Override
    public Boolean existsMemberByEmail(String email) {
        for(Long id:db.keySet()){
            if(db.get(id).getEmail().equals(email)){
                return false;
            }
        }
        return true;
    }


}
