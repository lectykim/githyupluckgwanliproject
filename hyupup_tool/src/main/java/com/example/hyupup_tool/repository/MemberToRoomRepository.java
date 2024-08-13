package com.example.hyupup_tool.repository;

import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberToRoomRepository extends JpaRepository<MemberToRoom, Long>,IMemberToRoomRepository {
    Optional<MemberToRoom> findMemberToRoomByMemberAndRoom(Member member, Room room);

    @Override
    Optional<MemberToRoom> findById(Long id);
    @Override
    MemberToRoom save(MemberToRoom memberToRoom);

    @Override
    void delete(MemberToRoom memberToRoom);
}
