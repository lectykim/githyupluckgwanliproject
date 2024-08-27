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

    List<MemberToRoom> findMemberToRoomByRoom(Room room);

    List<MemberToRoom> findMemberToRoomByMember(Member member);


    @Override
    @Query("select mtr from MemberToRoom mtr where mtr.room = :room and mtr.isMaster = :isMaster")
    Optional<MemberToRoom> findMemberToRoomByRoomAndMaster(@Param("room") Room room,@Param("isMaster") Boolean isMaster);


    @Override
    @Query("select mtr from MemberToRoom mtr where mtr.member = :member and mtr.isMaster = :isMaster")
    Optional<MemberToRoom> findMemberToRoomByMemberAndMaster(@Param("member") Member member,@Param("isMaster") Boolean isMaster);

    @Override
    Optional<MemberToRoom> findById(Long id);
    @Override
    MemberToRoom save(MemberToRoom memberToRoom);

    @Override
    void delete(MemberToRoom memberToRoom);
}
