package com.example.hyupup_tool.repository;

import com.example.hyupup_tool.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long>,IRoomRepository {
    @Override
    Optional<Room> findById(Long id);
    @Override
    Room save(Room room);

    @Override
    void delete(Room room);

}
