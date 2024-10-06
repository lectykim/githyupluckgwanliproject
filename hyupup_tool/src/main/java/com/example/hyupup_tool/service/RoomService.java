package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.room.*;
import org.springframework.stereotype.Service;

@Service
public interface RoomService {
    CreateRoomResponse createRoom(CreateRoomRequest request);
    UpdateRoomResponse updateRoom(UpdateRoomRequest request);
    DeleteRoomResponse deleteRoom(DeleteRoomRequest request);
    InviteMemberResponse inviteMember(InviteMemberRequest request);

    GetCurrentRoomResponse getCurrentRoom(GetCurrentRoomRequest request);

    GetAllEventResponse getAllEvent(GetAllEventRequest request);

    AcceptInviteResponse acceptInvite(AcceptInviteRequest request);

    DenyInviteReseponse denyInvite(DenyInviteRequest request);
}
