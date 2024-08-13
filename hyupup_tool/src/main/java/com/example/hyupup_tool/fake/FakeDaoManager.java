package com.example.hyupup_tool.fake;

import com.example.hyupup_tool.repository.IMemberRepository;
import com.example.hyupup_tool.repository.IMemberToRoomRepository;
import com.example.hyupup_tool.repository.IRoomRepository;
import lombok.Getter;

public class FakeDaoManager {
    @Getter
    private static final IMemberRepository iMemberRepository = new FakeIMemberRepository();
    @Getter
    private static final IMemberToRoomRepository iMemberToRoomRepository = new FakeIMemberToRoomRepository();
    @Getter
    private static final IRoomRepository iRoomRepository = new FakeIRoomRepository();

    public static void clearAll(){
        ((FakeIMemberRepository)iMemberRepository).getDb().clear();
        ((FakeIMemberToRoomRepository)iMemberToRoomRepository).getDb().clear();
        ((FakeIRoomRepository)iRoomRepository).getDb().clear();
    }

}
