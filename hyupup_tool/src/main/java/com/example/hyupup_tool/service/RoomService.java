package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.entity.UserToRoom;
import com.example.hyupup_tool.entity.dto.room.*;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.exception.server.ServerException;
import com.example.hyupup_tool.repository.RoomRepository;
import com.example.hyupup_tool.repository.UserRepository;
import com.example.hyupup_tool.repository.UserToRoomRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final UserToRoomRepository userToRoomRepository;
    @Transactional
    public CreateRoomResponse createRoom(CreateRoomRequest request){
        var roomEntity = Room.of(request.maxUser(),request.title());
        var userEntity = userRepository.findById(request.userId())
                .orElseThrow(()-> new BadRequestException("Can't find User"));



        var savedRoomEntity = roomRepository.save(roomEntity);
        var userToRoomEntity = UserToRoom.of(userEntity,savedRoomEntity,true);
        userToRoomRepository.save(userToRoomEntity);

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

    public InviteUserResponse inviteUser(InviteUserRequest request){
        // TODO: Redis를 이용한 초대 보내기 코드 작성
        return new InviteUserResponse();
    }

    @Transactional
    public ChangeMasterResponse changeMaster(ChangeMasterRequest request){
        var roomEntity = roomRepository.findById(request.roomId())
                .orElseThrow(()-> new BadRequestException("Can't find room"));

        var oldUserEntity = userRepository.findById(request.oldMasterId())
                .orElseThrow(()-> new BadRequestException("Can't find old user"));

        var newUserEntity = userRepository.findById(request.newMasterId())
                .orElseThrow(()-> new BadRequestException("Can't find new user"));

        var oldUserToRoomEntity = userToRoomRepository.findUsertoRoomByUserAndRoom(oldUserEntity,roomEntity)
                .orElseThrow(()-> new ServerException("Internal Server Error"));

        oldUserToRoomEntity.setMaster(false);

        var newUserToRoomEntity = userToRoomRepository.findUsertoRoomByUserAndRoom(newUserEntity,roomEntity)
                .orElseThrow(()-> new ServerException("Internal Server Error"));

        newUserToRoomEntity.setMaster(true);

        return new ChangeMasterResponse();
    }

}
