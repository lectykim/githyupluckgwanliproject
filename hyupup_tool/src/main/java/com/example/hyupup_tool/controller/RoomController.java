package com.example.hyupup_tool.controller;

import com.example.hyupup_tool.entity.dto.room.*;
import com.example.hyupup_tool.security.CustomUserDetails;
import com.example.hyupup_tool.service.RoomServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room-api")
@RequiredArgsConstructor
public class RoomController {

    private final RoomServiceImpl roomServiceImpl;

    @PostMapping("/create")
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody CreateRoomRequest request){
        var response = roomServiceImpl.createRoom(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateRoomResponse> updateRoom(@RequestBody UpdateRoomRequest request){
        var response = roomServiceImpl.updateRoom(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteRoomResponse> deleteRoom(@RequestBody DeleteRoomRequest request){
        var response = roomServiceImpl.deleteRoom(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/invite-other-member")
    public ResponseEntity<InviteMemberResponse> inviteMember(@RequestBody  InviteMemberRequest request){
        var response = roomServiceImpl.inviteMember(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-master")
    public ResponseEntity<ChangeMasterResponse> changeMaster(@RequestBody  ChangeMasterRequest request){
        var response = roomServiceImpl.changeMaster(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-current-room")
    public ResponseEntity<GetCurrentRoomResponse> getCurrentRoom(GetCurrentRoomRequest request){

        var response = roomServiceImpl.getCurrentRoom(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-all-event")
    public ResponseEntity<GetAllEventResponse> getAllEvent(GetAllEventRequest request){

        var response = roomServiceImpl.getAllEvent(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-before-chat")
    public ResponseEntity<GetBeforeChatContentResponse> getBeforeChat(GetBeforeChatContentRequest request){
        var response = roomServiceImpl.getBeforeChatContent(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/deny-invite")
    public ResponseEntity<DenyInviteReseponse> denyInvite(@RequestBody  DenyInviteRequest request){
        var response = roomServiceImpl.denyInvite(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/accept-invite")
    public ResponseEntity<AcceptInviteResponse> acceptInvite(@RequestBody AcceptInviteRequest request) {
        var response = roomServiceImpl.acceptInvite(request);

        return ResponseEntity.ok(response);
    }

}
