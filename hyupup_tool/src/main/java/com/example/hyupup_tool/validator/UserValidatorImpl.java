package com.example.hyupup_tool.validator;

import com.example.hyupup_tool.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserValidatorImpl implements UserValidator{

    private final UserRepository userRepository;

    @Override
    public boolean canSignup(String email) {
        return userRepository.existsUserByEmail(email);
    }
}
