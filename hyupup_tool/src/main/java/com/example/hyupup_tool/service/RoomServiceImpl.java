package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.dto.room.*;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.exception.server.ServerException;
import com.example.hyupup_tool.repository.RoomRepository;
import com.example.hyupup_tool.repository.MemberRepository;
import com.example.hyupup_tool.repository.MemberToRoomRepository;
import com.example.hyupup_tool.security.CustomUserDetails;
import com.example.hyupup_tool.util.SessionGetter;
import com.example.hyupup_tool.validator.RoomValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final MemberToRoomRepository memberToRoomRepository;
    private final RoomValidator roomValidator;
    @Transactional
    public CreateRoomResponse createRoom(CreateRoomRequest request){
        roomValidator.createRoomValidator(request);
        var memberId = SessionGetter.getCurrentMemberDto().getMemberId();
        var roomEntity = Room.of(request.maxMember(),request.title());
        var memberEntity = memberRepository.findById(memberId)
                .orElseThrow(()-> new BadRequestException("Can't find member"));



        var savedRoomEntity = roomRepository.save(roomEntity);
        var memberToRoomEntity = MemberToRoom.of(memberEntity,savedRoomEntity,true);
        memberToRoomRepository.save(memberToRoomEntity);

        return new CreateRoomResponse(savedRoomEntity.getRoomId());
    }

    public UpdateRoomResponse updateRoom(UpdateRoomRequest request){
        roomValidator.updateRoomValidator(request);
        var roomEntity = roomRepository.findById(request.roomId())
                .orElseThrow(()-> new BadRequestException("Can't find room"));

        roomEntity.setTitle(request.title());
        roomEntity.setMaxMember(request.maxMember());

        return new UpdateRoomResponse(request.roomId());
    }

    public DeleteRoomResponse deleteRoom(DeleteRoomRequest request){
        roomValidator.deleteRoomValidator(request);
        roomRepository.deleteById(request.roomId());
        return new DeleteRoomResponse();
    }

    public InviteMemberResponse inviteMember(InviteMemberRequest request){
        roomValidator.inviteMemberValidator(request);
        // TODO: Redis를 이용한 초대 보내기 코드 작성
        return new InviteMemberResponse();
    }

    @Override
    public GetCurrentRoomResponse getCurrentRoom(GetCurrentRoomRequest request) {
        roomValidator.getCurrentRoomValidator(request);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();

        var memberToRoomList = memberToRoomRepository.findMemberToRoomByMember(userDetails.getMember())
                .orElseThrow(()->new BadRequestException("Not exist Member"));

        List<RoomDTO> roomDTOList = memberToRoomList.stream()
                .map(MemberToRoom::getRoom)
                .map(Room::toDto)
                .toList();
        return new GetCurrentRoomResponse(roomDTOList);
    }

    @Transactional
    public ChangeMasterResponse changeMaster(ChangeMasterRequest request){
        roomValidator.changeMasterValidator(request);
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
