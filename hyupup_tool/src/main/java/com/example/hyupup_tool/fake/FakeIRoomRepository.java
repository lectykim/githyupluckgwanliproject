package com.example.hyupup_tool.fake;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.repository.IRoomRepository;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeIRoomRepository implements IRoomRepository {
    @Getter
    Map<Long, Room> db = new HashMap<>();

    AtomicLong idGenerator = new AtomicLong();
    @Override
    public Optional<Room> findById(Long id) {
        return db.get(id) == null ? Optional.empty() : Optional.of(db.get(id));
    }

    @Override
    public Room save(Room room) {
        if(room.getRoomId() != null){
            db.put(room.getRoomId(),room);
            return room;
        }
        var newId = idGenerator.addAndGet(1);
        FakeSetter.setField(room,"roomId",newId);

        db.put(newId,room);
        return room;
    }

    @Override
    public void delete(Room room) {
        db.remove(room.getRoomId());
    }

    @Override
    public Optional<List<MemberToRoom>> findMemberToRoomByRoomId(Room room) {
        return Optional.empty();
    }


}
