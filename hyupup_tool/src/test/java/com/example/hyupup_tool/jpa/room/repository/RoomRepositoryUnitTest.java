package com.example.hyupup_tool.jpa.room.repository;

import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.repository.RoomRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class RoomRepositoryUnitTest {

    @Autowired
    private RoomRepository roomRepository;

    @Test
    void empty(){
        assertNotNull(roomRepository);
    }

    @Test
    @DisplayName("성공 : room 도메인 생성")
    void saveRoom_success_01(){
        var roomId = 1L;
        var entity = Room.of(5L,"title");
        var saved = roomRepository.save(entity);

        assertNotNull(saved);
        assertEquals(saved.getRoomId(),roomId);
        assertNotNull(saved.getCreatedDate());
    }

    @Test
    @DisplayName("성공 : room ID 조회")
    void findRoomById_success_01(){
        var roomId = 3L;
        var roomList = List.of(
                Room.of(5L,"title"),
                Room.of(5L,"title"),
                Room.of(5L,"title")
        );
        roomRepository.saveAll(roomList);

        var savedRoom = roomRepository.findById(roomId);
        assertEquals(savedRoom.get().getRoomId(),roomId);
    }

}
