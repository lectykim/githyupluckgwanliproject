package com.example.hyupup_tool.domain.room.service;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.fake.FakeDaoManager;
import com.example.hyupup_tool.repository.IMemberRepository;
import com.example.hyupup_tool.repository.IMemberToRoomRepository;
import com.example.hyupup_tool.repository.IRoomRepository;
import com.example.hyupup_tool.util.AuthorityRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class RoomServiceTest {

    private final IRoomRepository roomRepository = FakeDaoManager.getIRoomRepository();

    private final IMemberToRoomRepository memberToRoomRepository = FakeDaoManager.getIMemberToRoomRepository();

    private final IMemberRepository memberRepository = FakeDaoManager.getIMemberRepository();
    @BeforeEach
    void clearAll(){
        FakeDaoManager.clearAll();
    }


    private static final Long MAX_ROOM_NUMBER = 5L;

    private static final String ROOM_TITLE = "TITLE";

    private static final String MEMBER_EMAIL = "email@email.com";

    private static final String MEMBER_PW = "password";

    private static final String GITHUB_ACCESS_TOKEN = "github_access_token";


    private static final AuthorityRole AUTHORITY_ROLE = AuthorityRole.ROLE_NORMAL_MEMBER;
    /*@Test
    @DisplayName("성공 : 방 개설")
    void success_make_room_01(){
        var roomEntity = Room.of(MAX_ROOM_NUMBER,ROOM_TITLE);

        var savedRoomEntity = roomRepository.save(roomEntity);

        assertNotNull(savedRoomEntity);
        assertEquals(savedRoomEntity.getRoomId(),1L);

    }*/

    /*@Test
    @DisplayName("성공 : 방장 변경")
    void success_change_master(){
        //멤버 저장
        var masterMemberEntity = Member.of(MEMBER_EMAIL,MEMBER_PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE);

        var slaveMemberEntity = Member.of("email@e.com",MEMBER_PW,GITHUB_ACCESS_TOKEN,AUTHORITY_ROLE);

        memberRepository.save(masterMemberEntity);
        memberRepository.save(slaveMemberEntity);

        //방 생성

        var roomEntity = Room.of(MAX_ROOM_NUMBER,ROOM_TITLE);

        roomRepository.save(roomEntity);

        var masterMemberToRoomEntity = MemberToRoom.of(masterMemberEntity,roomEntity,true);
        var slaveMemberToRoomEntity = MemberToRoom.of(slaveMemberEntity,roomEntity,false);

        //방 정보 저장

        memberToRoomRepository.save(masterMemberToRoomEntity);
        memberToRoomRepository.save(slaveMemberToRoomEntity);

        //1번 유저가 마스터로, 2번 유저가 슬레이브로 저장되었는지 확인하는 테스트
        var master1Entity = memberToRoomRepository.findByIsMasterTrueAndRoom(roomEntity);
        assertTrue(master1Entity.get().isMaster());
        assertEquals(master1Entity.get().getMemberToRoomId(),1L);
        //2번 유저를 마스터로, 1번 유저를 슬레이브로 변경
        masterMemberToRoomEntity.setMaster(false);
        slaveMemberToRoomEntity.setMaster(true);

        memberToRoomRepository.save(masterMemberToRoomEntity);
        memberToRoomRepository.save(slaveMemberToRoomEntity);

        var master2Entity = memberToRoomRepository.findByIsMasterTrueAndRoom(roomEntity);

        assertTrue(master2Entity.get().isMaster());
        assertEquals(master2Entity.get().getMemberToRoomId(),2L);

    }*/

}
