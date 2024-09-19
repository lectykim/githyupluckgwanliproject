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
    public ResponseEntity<CreateRoomResponse> createRoom(CreateRoomRequest request){
        var response = roomServiceImpl.createRoom(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateRoomResponse> updateRoom(UpdateRoomRequest request){
        var response = roomServiceImpl.updateRoom(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteRoomResponse> deleteRoom(DeleteRoomRequest request){
        var response = roomServiceImpl.deleteRoom(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/invite-other-member")
    public ResponseEntity<InviteMemberResponse> invitemember(InviteMemberRequest request){
        var response = roomServiceImpl.inviteMember(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-master")
    public ResponseEntity<ChangeMasterResponse> changeMaster(ChangeMasterRequest request){
        var response = roomServiceImpl.changeMaster(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-current-room")
    public ResponseEntity<GetCurrentRoomResponse> getCurrentRoom(GetCurrentRoomRequest request){

        var response = roomServiceImpl.getCurrentRoom(request);

        return ResponseEntity.ok(response);
    }

}
