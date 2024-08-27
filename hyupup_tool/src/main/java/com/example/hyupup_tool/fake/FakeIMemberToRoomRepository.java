package com.example.hyupup_tool.fake;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.repository.IMemberToRoomRepository;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeIMemberToRoomRepository implements IMemberToRoomRepository {
    @Getter
    Map<Long,Member> db = new HashMap<>();

    AtomicLong idGenerator = new AtomicLong();
    @Override
    public Optional<MemberToRoom> findMemberToRoomByMemberAndRoom(Member member, Room room) {
        return Optional.empty();
    }

    @Override
    public Optional<MemberToRoom> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public MemberToRoom save(MemberToRoom memberToRoom) {
        return null;
    }

    @Override
    public void delete(MemberToRoom memberToRoom) {

    }

    @Override
    public List<MemberToRoom> findMemberToRoomByRoom(Room room) {
        return null;
    }

    @Override
    public List<MemberToRoom> findMemberToRoomByMember(Member member) {
        return null;
    }

    @Override
    public Optional<MemberToRoom> findMemberToRoomByRoomAndMaster(Room room, Boolean isMaster) {
        return Optional.empty();
    }

    @Override
    public Optional<MemberToRoom> findMemberToRoomByMemberAndMaster(Member member, Boolean isMaster) {
        return Optional.empty();
    }


}
