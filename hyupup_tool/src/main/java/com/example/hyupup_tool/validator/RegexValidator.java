package com.example.hyupup_tool.validator;

import java.util.regex.Pattern;

public class RegexValidator {
    private static final String EMAIL_VALIDATE_REGEX = "/^(?=.{1,254}$)(?=.{1,64}@.{1,255}$)[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$/";

    private static final String PW_VALIDATE_REGEX = "/^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()])[A-Za-z\\d!@#$%^&*()]{8,31}$/";
    public static boolean validateEmail(String email){
        Pattern pattern = Pattern.compile(EMAIL_VALIDATE_REGEX);

        return pattern.matcher(email).matches();
    }

    public static boolean validatePw(String pw){
        Pattern pattern = Pattern.compile(PW_VALIDATE_REGEX);

        return pattern.matcher(pw).matches();
    }


}
