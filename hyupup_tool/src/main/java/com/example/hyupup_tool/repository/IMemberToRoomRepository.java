package com.example.hyupup_tool.repository;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;

import java.util.Optional;

public interface IMemberToRoomRepository {
    Optional<MemberToRoom> findMemberToRoomByMemberAndRoom(Member member, Room room);

    Optional<MemberToRoom> findById(Long id);
    MemberToRoom save(MemberToRoom memberToRoom);

    void delete(MemberToRoom memberToRoom);

}
