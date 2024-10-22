package com.example.hyupup_tool.jpa.member.repository;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.repository.MemberRepository;
import com.example.hyupup_tool.util.AuthorityRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;



@DataJpaTest
public class MemberRepositoryUnitTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void empty(){
        assertNotNull(memberRepository);
    }

    /*@Test
    @DisplayName("성공: 엔티티 정상 생성 및 저장")
    public void save_success(){
        var entity = Member.of("email","pw","access_token", AuthorityRole.NORMAL_MEMBER);
        var saved = memberRepository.save(entity);

        assertNotNull(saved.getMemberId());
        assertEquals(entity.getMemberId(),saved.getMemberId());

        assertNotNull(saved.getCreatedDate());
    }

    @Test
    @DisplayName("성공 : 유저 아이디 조회")
    public void findByUserId_success(){
        var userId = 1L;
        var entities = List.of(
                Member.of("email","pw","access_token", AuthorityRole.NORMAL_MEMBER),
                Member.of("email1","pw","access_token", AuthorityRole.NORMAL_MEMBER),
                Member.of("email2","pw","access_token", AuthorityRole.NORMAL_MEMBER),
                Member.of("email3","pw","access_token", AuthorityRole.NORMAL_MEMBER)
        );

        memberRepository.saveAll(entities);

        var found = memberRepository.findById(userId);
        assertEquals(found.get().getMemberId(),userId);
    }*/


}
