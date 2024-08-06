package com.example.hyupup_tool.repository;

import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.entity.User;
import com.example.hyupup_tool.entity.UserToRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserToRoomRepository extends JpaRepository<UserToRoom, Long> {
    Optional<UserToRoom> findUsertoRoomByUserAndRoom(User user, Room room);
}
