package com.example.hyupup_tool.controller;

import com.example.hyupup_tool.entity.dto.user.*;
import com.example.hyupup_tool.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/user-api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest){
        LoginResponse response = userService.login(loginRequest);

        // TODO: 세션 맺기

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(SignupRequest signupRequest){
        SignupResponse response = userService.signup(signupRequest);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/can-signup-id")
    public ResponseEntity<CanSignupIdResponse> canSignupId(String email){
        return ResponseEntity.ok(userService.canSignupId(email));
    }




    @PostMapping("/modify-user-info")
    public ResponseEntity<ModifyUserInfoResponse> modifyUserInfo(ModifyUserInfoRequest request){
        var response = userService.modifyUserInfo(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> logout(){
        return ResponseEntity.ok(true);
    }

}
