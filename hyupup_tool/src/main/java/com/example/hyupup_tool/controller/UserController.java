package com.example.hyupup_tool.controller;

import com.example.hyupup_tool.entity.dto.*;
import com.example.hyupup_tool.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/v1/user-api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email,
                                          @RequestParam String pw,
                                          HttpServletRequest httpServletRequest){
        LoginDTO loginDTO = userService.login(email,pw);
        if(loginDTO != null){
            httpServletRequest.setAttribute("UserId",loginDTO.getUserId());
            return ResponseEntity.ok(loginDTO);
        }
        ControllerErrorResponse controllerErrorResponse = new ControllerErrorResponse("Login failed");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(controllerErrorResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequestDTO signUpRequestDTO){
        return userService.signup(signUpRequestDTO);
    }

    @PostMapping("/modify-user-info")
    public ResponseEntity<?> modifyUserInfo(@RequestBody UserDTO userDTO,HttpServletRequest httpServletRequest){
        if(httpServletRequest.getSession(false) == null){
            ControllerErrorResponse controllerErrorResponse = new ControllerErrorResponse("Not Login");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(controllerErrorResponse);
        }
        userDTO.setUserId((Long)httpServletRequest.getAttribute("UserId"));
        return userService.modifyUserInfo(userDTO);
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().invalidate();
        SuccessResponseDTO successResponseDTO = new SuccessResponseDTO("Logout success");
        return ResponseEntity.ok(successResponseDTO);
    }

}
