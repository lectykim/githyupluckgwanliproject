package com.example.hyupup_tool.controller;

import com.example.hyupup_tool.entity.dto.room.*;
import com.example.hyupup_tool.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/api/v1/room-api")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping("/create")
    public ResponseEntity<CreateRoomResponse> createRoom(CreateRoomRequest request){
        var response = roomService.createRoom(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/update")
    public ResponseEntity<UpdateRoomResponse> updateRoom(UpdateRoomRequest request){
        var response = roomService.updateRoom(request);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<DeleteRoomResponse> deleteRoom(DeleteRoomRequest request){
        var response = roomService.deleteRoom(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/invite-other-member")
    public ResponseEntity<InviteMemberResponse> invitemember(InviteMemberRequest request){
        var response = roomService.invitemember(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/change-master")
    public ResponseEntity<ChangeMasterResponse> changeMaster(ChangeMasterRequest request){
        var response = roomService.changeMaster(request);

        return ResponseEntity.ok(response);
    }

}
