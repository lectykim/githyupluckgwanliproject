package com.example.hyupup_tool.validator;

import com.example.hyupup_tool.entity.dto.room.*;

public interface RoomValidator {
    void createRoomValidator(CreateRoomRequest request);

    void updateRoomValidator(UpdateRoomRequest request);

    void deleteRoomValidator(DeleteRoomRequest request);

    void inviteMemberValidator(InviteMemberRequest request);

    void changeMasterValidator(ChangeMasterRequest request);
}
