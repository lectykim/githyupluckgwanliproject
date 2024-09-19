package com.example.hyupup_tool.fake;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.repository.IMemberToRoomRepository;
import lombok.Getter;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class FakeIMemberToRoomRepository implements IMemberToRoomRepository {
    @Getter
    Map<Long,MemberToRoom> db = new HashMap<>();

    AtomicLong idGenerator = new AtomicLong();
    @Override
    public Optional<MemberToRoom> findMemberToRoomByMemberAndRoom(Member member, Room room) {
        return Optional.empty();
    }

    @Override
    public Optional<MemberToRoom> findById(Long id) {
        return db.get(id) == null ? Optional.empty() : Optional.of(db.get(id));
    }

    @Override
    public MemberToRoom save(MemberToRoom memberToRoom) {
        if(memberToRoom.getMemberToRoomId() != null){
            db.put(memberToRoom.getMemberToRoomId(),memberToRoom);
            return memberToRoom;
        }
        var newId = idGenerator.addAndGet(1);
        FakeSetter.setField(memberToRoom,"memberToRoomId",newId);

        db.put(newId,memberToRoom);
        return memberToRoom;
    }

    @Override
    public void delete(MemberToRoom memberToRoom) {
        db.remove(memberToRoom.getMemberToRoomId());
    }

    @Override
    public Optional<List<MemberToRoom>> findMemberToRoomByRoom(Room room) {
        List<MemberToRoom> memberToRoomList = new ArrayList<>();
        for(Long id:db.keySet()){
            if(Objects.equals(db.get(id).getRoom().getRoomId(), room.getRoomId())){
                memberToRoomList.add(db.get(id));
            }
        }
        return Optional.of(memberToRoomList);
    }

    @Override
    public Optional<List<MemberToRoom>> findMemberToRoomByMember(Member member) {
        List<MemberToRoom> memberToRoomList = new ArrayList<>();
        for(Long id:db.keySet()){
            if(Objects.equals(db.get(id).getMember().getMemberId(), member.getMemberId())){
                memberToRoomList.add(db.get(id));
            }
        }
        return Optional.of(memberToRoomList);
    }

    @Override
    public Optional<MemberToRoom> findByIsMasterTrueAndRoom(Room room){
        for(Long id:db.keySet()){
            if(db.get(id).isMaster() && db.get(id).getRoom().getRoomId().equals(room.getRoomId())){
                return Optional.of(db.get(id));
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<MemberToRoom> findByIsMasterTrueAndMember(Member member){
        for(Long id:db.keySet()){
            if(db.get(id).isMaster() && db.get(id).getMember().getMemberId().equals(member.getMemberId())){
                return Optional.of(db.get(id));
            }
        }
        return Optional.empty();
    }


}
