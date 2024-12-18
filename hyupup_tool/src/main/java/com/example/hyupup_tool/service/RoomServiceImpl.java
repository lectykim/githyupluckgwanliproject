package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.Member;
import com.example.hyupup_tool.entity.MemberToRoom;
import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.entity.dto.member.MemberDTO;
import com.example.hyupup_tool.entity.dto.room.*;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.exception.server.ServerException;
import com.example.hyupup_tool.repository.MemberRepository;
import com.example.hyupup_tool.repository.MemberToRoomRepository;
import com.example.hyupup_tool.repository.RoomRepository;
import com.example.hyupup_tool.security.CustomUserDetails;
import com.example.hyupup_tool.util.SessionGetter;
import com.example.hyupup_tool.validator.RoomValidator;
import com.example.hyupup_tool.websocket.MessageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{

    private final RedisTemplate<String,String> redisTemplate;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final MemberToRoomRepository memberToRoomRepository;
    private final RoomValidator roomValidator;
    private final SessionGetter sessionGetter;
    private final MessageService messageService;
    @Transactional
    public CreateRoomResponse createRoom(CreateRoomRequest request){
        roomValidator.createRoomValidator(request);
        var memberId = SessionGetter.getCurrentMemberDto().getMemberId();
        var roomEntity = Room.of(request.maxMember(),request.title(),request.repository(),request.owner());
        var memberEntity = memberRepository.findById(memberId)
                .orElseThrow(()-> new BadRequestException("Can't find member"));



        var savedRoomEntity = roomRepository.save(roomEntity);
        var memberToRoomEntity = MemberToRoom.of(memberEntity,savedRoomEntity,true);
        memberToRoomRepository.save(memberToRoomEntity);
        sessionGetter.resetCurrentMemberDto();
        return new CreateRoomResponse(savedRoomEntity.getRoomId());
    }

    public UpdateRoomResponse updateRoom(UpdateRoomRequest request){
        roomValidator.updateRoomValidator(request);
        var roomEntity = roomRepository.findById(request.roomId())
                .orElseThrow(()-> new BadRequestException("Can't find room"));

        roomEntity.setRepository(request.repository());
        roomEntity.setOwner(request.owner());
        roomRepository.save(roomEntity);
        sessionGetter.resetCurrentMemberDto();
        return new UpdateRoomResponse(request.roomId());
    }

    public DeleteRoomResponse deleteRoom(DeleteRoomRequest request){
        roomValidator.deleteRoomValidator(request);
        roomRepository.deleteById(request.roomId());
        sessionGetter.resetCurrentMemberDto();
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
                .peek(s-> s.setMessageHistory(
                        messageService.getMessageList(s.getRoomId())
                ))
                .toList();
        roomDTOList = messageService.setNotReadMessageCounts(roomDTOList,userDetails.getMember());

        return new GetCurrentRoomResponse(roomDTOList);
    }

    @Override
    public GetAllEventResponse getAllEvent(GetAllEventRequest request) {
        var memberId = SessionGetter.getCurrentMemberDto().getMemberId();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getDetails();
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        Map<String,String> entries = hashOperations.entries("Invite:"+memberId);
        Iterable<Long> hashIterable = entries.keySet()
                .stream()
                        .map(Long::parseLong)
                                .collect(Collectors.toList());
        var roomDTOList = roomRepository.findAllById(hashIterable)
                .stream()
                .map(Room::toDto)
                .peek(s-> s.setMessageHistory(
                        messageService.getMessageList(s.getRoomId())
                ))
                .toList();
        var memberToRoomList = memberToRoomRepository.findMemberToRoomByMember(userDetails.getMember())
                .orElseThrow(()->new BadRequestException("Not exist Member"));

        List<RoomDTO> currentRoomDTOList = memberToRoomList.stream()
                .map(MemberToRoom::getRoom)
                .map(Room::toDto)
                .peek(s-> s.setMessageHistory(
                        messageService.getMessageList(s.getRoomId())
                ))
                .toList();

        return new GetAllEventResponse(roomDTOList,currentRoomDTOList,userDetails.getMember().toDto());
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
                hashOperations.delete("Invite:"+memberId,key.toString());
                var member = memberRepository.findById(memberId)
                        .orElseThrow(()-> new BadRequestException("Member not found"));
                var room = roomRepository.findById(key)
                        .orElseThrow(()->new BadRequestException("Room not found"));
                if(memberToRoomRepository.existsByMemberAndRoom(member,room)){
                    throw new BadRequestException("Already joined this room");
                }
                var memberToRoom  = MemberToRoom.of(member,room,false);
                memberToRoomRepository.save(memberToRoom);
                return new AcceptInviteResponse();
            }
        }
        sessionGetter.resetCurrentMemberDto();
        throw new BadRequestException("Invalid invite");
    }

    @Override
    public DenyInviteReseponse denyInvite(DenyInviteRequest request) {
        var memberId = SessionGetter.getCurrentMemberDto().getMemberId();
        HashOperations<String,String,String> hashOperations = redisTemplate.opsForHash();
        Map<String,String> entries = hashOperations.entries("Invite:"+memberId);
        Iterable<Long> hashIterable = entries.keySet()
                .stream()
                .map(Long::parseLong)
                .collect(Collectors.toList());

        for(Long key: hashIterable){
            if(Objects.equals(request.roomId(),key)){
                hashOperations.delete("Invite:"+memberId,key.toString());
                return new DenyInviteReseponse();
            }
        }
        throw new BadRequestException("Invalid deny");
    }

    @Override
    public GetBeforeChatContentResponse getBeforeChatContent(GetBeforeChatContentRequest request) {
        var key = "Room:"+request.roomId()+":Chat";
        List<String> messageList = redisTemplate.opsForList().range(key, 0, -1);
        if (messageList != null) {
            List<MessageDTO> messageDTOList = messageList.stream()
                    .map(s -> MessageDTO.makeMessage(s, request.roomId()))  // 각 String을 MessageDTO로 변환
                    .collect(Collectors.toList());  // 변환된 결과를 List로 수집
            return new GetBeforeChatContentResponse(messageDTOList);
        } else {
            return new GetBeforeChatContentResponse(null);
        }




    }

    @Override
    public SyncRoomReadPosResponse syncRoomReadPos(SyncRoomReadPosRequest request) {
        var member = SessionGetter.getCurrentMemberDto();
        String key = "Room:"+request.roomId()+"Member:"+member.getEmail()+"ReadPos";
        Long value = redisTemplate.opsForList().size("Room:"+request.roomId()+":Chat");
        if(value != null){
            redisTemplate.opsForValue().set(key,value.toString());
        }
        return new SyncRoomReadPosResponse();

    }

    @Override
    public GetCurrentMemberResponse getCurrentMember(GetCurrentMemberRequest request) {
        var roomEntity = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new BadRequestException("Can't find room"));

        var memberToRoomList = roomRepository.findMemberToRoomByRoomId(roomEntity)
                .orElseThrow(() -> new BadRequestException("Can't find member"));

        List<MemberDTO> memberDTOList = memberToRoomList.stream()
                .map(memberToRoom -> memberToRoom.getMember().toDto())
                .collect(Collectors.toList());

        return new GetCurrentMemberResponse(memberDTOList);
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
