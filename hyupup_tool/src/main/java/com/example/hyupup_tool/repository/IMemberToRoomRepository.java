package com.example.hyupup_tool.repository;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;

import java.util.List;
import java.util.Optional;

public interface IMemberToRoomRepository {
    Optional<MemberToRoom> findMemberToRoomByMemberAndRoom(Member member, Room room);

    Optional<MemberToRoom> findById(Long id);
    MemberToRoom save(MemberToRoom memberToRoom);

    boolean existsByMemberAndRoom(Member member, Room room);
    void delete(MemberToRoom memberToRoom);

    Optional<List<MemberToRoom>> findMemberToRoomByRoom(Room room);

    Optional<List<MemberToRoom>> findMemberToRoomByMember(Member member);

    Optional<MemberToRoom> findByIsMasterTrueAndRoom(Room room);

    Optional<MemberToRoom> findByIsMasterTrueAndMember(Member member);


}
