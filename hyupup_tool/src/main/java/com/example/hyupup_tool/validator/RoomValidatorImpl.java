package com.example.hyupup_tool.validator;

import com.example.hyupup_tool.entity.Room;
import com.example.hyupup_tool.entity.dto.room.*;
import com.example.hyupup_tool.repository.IMemberRepository;
import com.example.hyupup_tool.repository.IMemberToRoomRepository;
import com.example.hyupup_tool.repository.IRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomValidatorImpl implements RoomValidator{

    private final IMemberRepository memberRepository;
    private final IMemberToRoomRepository memberToRoomRepository;
    private final IRoomRepository roomRepository;

    @Override
    public void createRoomValidator(CreateRoomRequest request) {
        Room.validateTitle(request.title());
        Room.validateMaxMember(request.maxMember());
    }

    @Override
    public void updateRoomValidator(UpdateRoomRequest request) {
        Room.validateTitle(request.title());
        Room.validateMaxMember(request.maxMember());
    }

    @Override
    public void deleteRoomValidator(DeleteRoomRequest request) {

    }

    @Override
    public void inviteMemberValidator(InviteMemberRequest request) {

    }

    @Override
    public void changeMasterValidator(ChangeMasterRequest request) {

    }
}
