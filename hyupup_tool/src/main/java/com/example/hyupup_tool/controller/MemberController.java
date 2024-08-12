package com.example.hyupup_tool.controller;

import com.example.hyupup_tool.entity.dto.member.*;
import com.example.hyupup_tool.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/member-api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest){
        LoginResponse response = memberService.login(loginRequest);

        // TODO: 세션 맺기

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest){
        SignupResponse response = memberService.signup(signupRequest);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/can-signup-id")
    public ResponseEntity<CanSignupIdResponse> canSignupId(String email){
        return ResponseEntity.ok(memberService.canSignupId(email));
    }




    @PostMapping("/modify-member-info")
    public ResponseEntity<ModifyMemberInfoResponse> modifymemberInfo(ModifyMemberInfoRequest request){
        var response = memberService.modifyMemberInfo(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout(){
        return ResponseEntity.ok(true);
    }

}
