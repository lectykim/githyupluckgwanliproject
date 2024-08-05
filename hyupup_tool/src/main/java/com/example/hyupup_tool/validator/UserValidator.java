package com.example.hyupup_tool.validator;

import com.example.hyupup_tool.entity.dto.user.SignupRequest;

public interface UserValidator {

    boolean canSignup(String email);

}
