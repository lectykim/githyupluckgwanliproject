package com.example.hyupup_tool.fake;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.repository.IRoomRepository;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeIRoomRepository implements IRoomRepository {
    @Getter
    Map<Long, Member> db = new HashMap<>();

    AtomicLong idGenerator = new AtomicLong();
    @Override
    public Optional<Room> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Room save(Room room) {
        return null;
    }

    @Override
    public void delete(Room room) {

    }
}
