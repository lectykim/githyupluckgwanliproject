package com.example.hyupup_tool.service;

import com.example.hyupup_tool.entity.dto.LoginDTO;
import com.example.hyupup_tool.entity.dto.SignUpRequestDTO;
import com.example.hyupup_tool.entity.dto.SuccessResponseDTO;
import com.example.hyupup_tool.entity.dto.UserDTO;
import com.example.hyupup_tool.entity.user.User;
import com.example.hyupup_tool.exception.ModifyUserInfoFailException;
import com.example.hyupup_tool.exception.SignUpFailException;
import com.example.hyupup_tool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public LoginDTO login(String email, String pw) {
        Optional<User> userOptional = userRepository.findUserByEmailAndPw(email,pw);

        if(userOptional.isPresent()){
            User user = userOptional.get();
            LoginDTO loginDTO = new LoginDTO();
            loginDTO.setEmail(user.getEmail());
            loginDTO.setPurchasePlan(user.getPurchasePlan());
            return loginDTO;

        }else{
            return null;
        }
    }

    public ResponseEntity<?> signup(SignUpRequestDTO signUpRequestDTO) {
        User user = signUpRequestDTO.toUserEntity();
        try{
            userRepository.save(user);
        } catch (DataIntegrityViolationException e){
            throw new SignUpFailException(e.getMessage());
        }

        SuccessResponseDTO successResponseDTO = new SuccessResponseDTO("Sign up Success");
        return ResponseEntity.ok(successResponseDTO);
    }

    public ResponseEntity<?> modifyUserInfo(UserDTO userDTO) {
        Optional<User> user = userRepository.findById(userDTO.getUserId());
        if(user.isPresent()){
            try {
                userRepository.save(userDTO.toUserEntity());
            } catch (DataIntegrityViolationException e){
                throw new ModifyUserInfoFailException(e.getMessage());
            }

            SuccessResponseDTO successResponseDTO = new SuccessResponseDTO("modify success");
            return ResponseEntity.ok(successResponseDTO);
        }else{
            throw new ModifyUserInfoFailException("User is not exist.");
        }
    }
}
