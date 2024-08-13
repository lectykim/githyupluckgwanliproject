package com.example.hyupup_tool.fake;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.repository.IMemberToRoomRepository;
import lombok.Getter;

import java.util.HashMap;
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
}
