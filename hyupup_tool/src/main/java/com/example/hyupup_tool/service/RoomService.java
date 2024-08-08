package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.dto.room.*;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.exception.server.ServerException;
import com.example.hyupup_tool.repository.RoomRepository;
import com.example.hyupup_tool.repository.MemberRepository;
import com.example.hyupup_tool.repository.MemberToRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final MemberToRoomRepository memberToRoomRepository;
    @Transactional
    public CreateRoomResponse createRoom(CreateRoomRequest request){
        var roomEntity = Room.of(request.maxMember(),request.title());
        var memberEntity = memberRepository.findById(request.memberId())
                .orElseThrow(()-> new BadRequestException("Can't find member"));



        var savedRoomEntity = roomRepository.save(roomEntity);
        var memberToRoomEntity = MemberToRoom.of(memberEntity,savedRoomEntity,true);
        memberToRoomRepository.save(memberToRoomEntity);

        return new CreateRoomResponse(savedRoomEntity.getRoomId());
    }

    public UpdateRoomResponse updateRoom(UpdateRoomRequest request){
        var roomEntity = roomRepository.findById(request.roomId())
                .orElseThrow(()-> new BadRequestException("Can't find room"));

        roomEntity.setTitle(request.newTitle());

        return new UpdateRoomResponse(request.roomId());
    }

    public DeleteRoomResponse deleteRoom(DeleteRoomRequest request){
        roomRepository.deleteById(request.roomId());
        return new DeleteRoomResponse();
    }

    public InviteMemberResponse invitemember(InviteMemberRequest request){
        // TODO: Redis를 이용한 초대 보내기 코드 작성
        return new InviteMemberResponse();
    }

    @Transactional
    public ChangeMasterResponse changeMaster(ChangeMasterRequest request){
        var roomEntity = roomRepository.findById(request.roomId())
                .orElseThrow(()-> new BadRequestException("Can't find room"));

        var oldMemberEntity = memberRepository.findById(request.oldMasterId())
                .orElseThrow(()-> new BadRequestException("Can't find old member"));

        var newMemberEntity = memberRepository.findById(request.newMasterId())
                .orElseThrow(()-> new BadRequestException("Can't find new member"));

        var oldMemberToRoomEntity = memberToRoomRepository.findMemberToRoomByMemberAndRoom(oldMemberEntity,roomEntity)
                .orElseThrow(()-> new ServerException("Internal Server Error"));

        oldMemberToRoomEntity.setMaster(false);

        var newUserToRoomEntity = memberToRoomRepository.findMemberToRoomByMemberAndRoom(newMemberEntity,roomEntity)
                .orElseThrow(()-> new ServerException("Internal Server Error"));

        newUserToRoomEntity.setMaster(true);

        return new ChangeMasterResponse();
    }

}
