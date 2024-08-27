package com.example.hyupup_tool.jpa.memberToRoom.repository;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.repository.MemberRepository;
import com.example.hyupup_tool.repository.MemberToRoomRepository;
import com.example.hyupup_tool.repository.RoomRepository;
import com.example.hyupup_tool.util.AuthorityRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class MemberToRoomRepositoryUnitTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MemberToRoomRepository memberToRoomRepository;

    @Test
    void empty(){
        assertNotNull(memberRepository);
        assertNotNull(roomRepository);
        assertNotNull(memberToRoomRepository);
    }

    private static final String EMAIL = "email";
    private static final String PW = "pw";

    private static final String GITHUB_ACCESS_TOKEN = "github_access_token";

    private static final AuthorityRole AUTHORITY_ROLE = AuthorityRole.NORMAL_MEMBER;

    private static final Long MAX_MEMBER = 5L;

    private static final String ROOM_TITLE = "roomTitle";

    @BeforeEach
    void first_member_setup(){
        var memberEntity = Member.of(EMAIL,PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE);
        var roomEntity = Room.of(MAX_MEMBER,ROOM_TITLE);

        memberRepository.save(memberEntity);
        roomRepository.save(roomEntity);

        var memberToRoomEntity = MemberToRoom.of(memberEntity,roomEntity,true);
        memberToRoomRepository.save(memberToRoomEntity);
    }

    @Test
    @DisplayName("성공 : 첫 방을 생성한 유저가 방장이 되는 경우 조회")
    void find_member_to_room_entity_success_01(){
        var entity = memberToRoomRepository.findById(1L);
        assertEquals(entity.get().getMemberToRoomId(),1L);
        assertEquals(entity.get().getMember().getMemberId(),1L);
        assertEquals(entity.get().getRoom().getRoomId(),1L);

        assertEquals(entity.get().getMember().getEmail(),EMAIL);
        assertEquals(entity.get().getRoom().getTitle(),ROOM_TITLE);
    }

    @Test
    @DisplayName("성공 : 하나의 방에 5명의 구성원을 생성 후 멤버를 조회하는 과정")
    void join_other_member_success_01(){
        var roomEntity = roomRepository.findById(1L)
                .orElseThrow();

        var memberEntityList = List.of(
                Member.of(EMAIL,PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE),
                Member.of(EMAIL,PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE),
                Member.of(EMAIL,PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE),
                Member.of(EMAIL,PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE),
                Member.of(EMAIL,PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE)
        );

        memberRepository.saveAll(memberEntityList);

        for(var memberEntity : memberEntityList){
            var memberToRoomEntity = MemberToRoom.of(memberEntity,roomEntity,false);
            memberToRoomRepository.save(memberToRoomEntity);
        }

        var memberToRoomEntityList = memberToRoomRepository.findMemberToRoomByRoom(roomEntity);

        for(var memberToRoom : memberToRoomEntityList){
            assertEquals(memberToRoom.getRoom().getRoomId(),roomEntity.getRoomId());
            assertEquals(memberToRoom.getMember().getEmail(),EMAIL);
            assertEquals(memberToRoom.getRoom().getTitle(),ROOM_TITLE);
        }


    }


    @Test
    @DisplayName("성공 : 한 명의 유저가 5개의 방에 들어간 경우")
    void find_member_to_room_entity_success_02(){
        var memberEntity = memberRepository.findById(1L)
                .orElseThrow();

        var roomEntityList = List.of(
                Room.of(MAX_MEMBER,ROOM_TITLE),
                Room.of(MAX_MEMBER,ROOM_TITLE),
                Room.of(MAX_MEMBER,ROOM_TITLE),
                Room.of(MAX_MEMBER,ROOM_TITLE),
                Room.of(MAX_MEMBER,ROOM_TITLE)
        );

        roomRepository.saveAll(roomEntityList);

        for(var room: roomEntityList){
            var memberToRoomEntity = MemberToRoom.of(memberEntity,room,false);
            memberToRoomRepository.save(memberToRoomEntity);
        }

        var memberToRoomEntityList = memberToRoomRepository.findMemberToRoomByMember(memberEntity);

        for(var mtr : memberToRoomEntityList){
            assertEquals(mtr.getRoom().getMaxMember(),MAX_MEMBER);
            assertEquals(mtr.getMember().getMemberId(),memberEntity.getMemberId());
            assertEquals(mtr.getRoom().getTitle(),ROOM_TITLE);
        }


    }

    @Test
    @DisplayName("성공 : 방장 변경")
    void change_new_master(){
        var memberEntity = Member.of(EMAIL,PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE);
        var roomEntity = roomRepository.findById(1L)
                        .orElseThrow();

        //새로운 유저 저장
        memberRepository.save(memberEntity);

        //새 유저는 master가 아님
        var newMemberToRoomEntity = MemberToRoom.of(memberEntity,roomEntity,false);

        memberToRoomRepository.save(newMemberToRoomEntity);

        //마스터 유저 조회
        var oldMemberToRoomEntity = memberToRoomRepository.findMemberToRoomByRoomAndMaster(roomEntity,true);

        //마스터 유저 권한과 슬레이브 유저 권한을 바꿔치기
        oldMemberToRoomEntity.get().setMaster(false);
        newMemberToRoomEntity.setMaster(true);


    }
}
