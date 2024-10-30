package com.example.hyupup_tool.controller.ssr;

import com.example.hyupup_tool.entity.dto.member.GetMemberInfoRequest;
import com.example.hyupup_tool.entity.dto.room.GetAllEventRequest;
import com.example.hyupup_tool.entity.dto.room.GetCurrentRoomRequest;
import com.example.hyupup_tool.service.MemberService;
import com.example.hyupup_tool.service.RoomService;
import com.example.hyupup_tool.util.SessionGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final RoomService roomService;
    private final MemberService memberService;
    @GetMapping("/main")
    private String dashboardMainPage(Model model){
        var getCurrentRoomResponse = roomService.getCurrentRoom(new GetCurrentRoomRequest());
        var currentMemberDTO = SessionGetter.getCurrentMemberDto();

        model.addAttribute("roomDtoList",getCurrentRoomResponse.roomDTOList());
        model.addAttribute("currentMemberDTO",currentMemberDTO);

        return "/dashboard";
    }

    @GetMapping("/mypage")
    private String dashboardMyPage(Model model){
        var response  = memberService.getMemberInfo(new GetMemberInfoRequest());

        model.addAttribute("memberDto",response.memberDTO());
        return "/mypage";
    }


}
