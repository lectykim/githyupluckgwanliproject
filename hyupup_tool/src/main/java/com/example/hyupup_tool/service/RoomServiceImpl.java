package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.entity.dto.room.*;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.exception.server.ServerException;
import com.example.hyupup_tool.repository.MemberRepository;
import com.example.hyupup_tool.repository.MemberToRoomRepository;
import com.example.hyupup_tool.repository.RoomRepository;
import com.example.hyupup_tool.security.CustomUserDetails;
import com.example.hyupup_tool.util.SessionGetter;
import com.example.hyupup_tool.validator.RoomValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RedisTemplate<String,String> redisTemplate;
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

        HashOperations<String, String,String> hashOperations = redisTemplate.opsForHash();
        var member = memberRepository.findMemberByEmail(request.email())
                        .orElseThrow(()-> new BadRequestException("Member not found"));
        hashOperations.put("Invite:"+member.getMemberId(),request.roomId().toString(),"false");
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

    @Override
    public GetAllEventResponse getAllEvent(GetAllEventRequest request) {
        var memberId = SessionGetter.getCurrentMemberDto().getMemberId();
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        Map<String,String> entries = hashOperations.entries("Invite:"+memberId);
        Iterable<Long> hashIterable = entries.keySet()
                .stream()
                        .map(Long::parseLong)
                                .collect(Collectors.toList());
        var roomDTOList = roomRepository.findAllById(hashIterable)
                .stream()
                .map(Room::toDto)
                .toList();
        return new GetAllEventResponse(roomDTOList);
    }

    @Override
    public AcceptInviteResponse acceptInvite(AcceptInviteRequest request) {
        var memberId = SessionGetter.getCurrentMemberDto().getMemberId();
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        Map<String,String> entries = hashOperations.entries("Invite:"+memberId);
        Iterable<Long> hashIterable = entries.keySet()
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        for (Long key : hashIterable) {
            if(Objects.equals(request.roomId(), key)){
                hashOperations.delete("Invite:"+memberId,key);
                var member = memberRepository.findById(memberId)
                        .orElseThrow(()-> new BadRequestException("Member not found"));
                var room = roomRepository.findById(key)
                        .orElseThrow(()->new BadRequestException("Room not found"));
                var memberToRoom  = MemberToRoom.of(member,room,false);
                memberToRoomRepository.save(memberToRoom);
                return new AcceptInviteResponse();
            }
        }
        throw new BadRequestException("Invalid invite");
    }

    @Override
    public DenyInviteReseponse denyInvite(DenyInviteRequest request) {
        return null;
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
