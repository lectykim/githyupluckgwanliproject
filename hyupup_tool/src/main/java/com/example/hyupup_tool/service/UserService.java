package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.*;
import com.example.hyupup_tool.entity.User;
import com.example.hyupup_tool.entity.dto.user.*;
import com.example.hyupup_tool.exception.client.BadRequestException;
import com.example.hyupup_tool.exception.client.UnauthorizedException;
import com.example.hyupup_tool.exception.server.ServerException;
import com.example.hyupup_tool.repository.UserRepository;
import com.example.hyupup_tool.validator.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    public LoginResponse login(LoginRequest loginRequest) {
        Optional<User> userOptional = userRepository.findUserByEmailAndPw(loginRequest.email(),loginRequest.pw());

        if(userOptional.isEmpty()){
            throw new UnauthorizedException("Email and Password not correct");
        }

        return new LoginResponse(userOptional.get().getUserId());
    }

    @Transactional
    public SignupResponse signup(SignupRequest signupRequest) {
        if(!userValidator.canSignup(signupRequest.email())){
            throw new BadRequestException("Already signup email");
        }

        User user = User.of(signupRequest.email(),
                signupRequest.pw(),
                signupRequest.githubAccessToken()
        );

        var insertedUser = userRepository.save(user);

        return new SignupResponse(insertedUser.getUserId());


    }

    public CanSignupIdResponse canSignupId(String email){
        return new CanSignupIdResponse(userValidator.canSignup(email));
    }

    @Transactional
    public ModifyUserInfoResponse modifyUserInfo(ModifyUserInfoRequest request) {
        var user = userRepository.findById(request.userId());
        if(user.isPresent()){
            try {
                var updatedUser = userRepository.save(User.of(request.email(),
                        request.pw(),
                        request.githubAccessToken())
                );
                return new ModifyUserInfoResponse(updatedUser.getUserId());
            } catch (DataIntegrityViolationException e){
                throw new ServerException(e.getMessage());
            }


        }else{
            throw new ServerException("User is not exist.");
        }
    }
}
