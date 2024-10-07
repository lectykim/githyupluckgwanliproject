package com.example.hyupup_tool.repository;

import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberToRoomRepository extends JpaRepository<MemberToRoom, Long>,IMemberToRoomRepository {
    Optional<MemberToRoom> findMemberToRoomByMemberAndRoom(Member member, Room room);

    Optional<List<MemberToRoom>> findMemberToRoomByRoom(Room room);

    Optional<List<MemberToRoom>> findMemberToRoomByMember(Member member);


    boolean existsByMemberAndRoom(Member member, Room room);
    @Override
    Optional<MemberToRoom> findByIsMasterTrueAndRoom(Room room);


    @Override
    Optional<MemberToRoom> findByIsMasterTrueAndMember(Member member);

    @Override
    Optional<MemberToRoom> findById(Long id);
    @Override
    MemberToRoom save(MemberToRoom memberToRoom);

    @Override
    void delete(MemberToRoom memberToRoom);
}
