package com.example.hyupup_tool.repository;

import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;

import java.util.Optional;

public interface IRoomRepository {
    Optional<Room> findById(Long id);
    Room save(Room room);

    void delete(Room room);
}
