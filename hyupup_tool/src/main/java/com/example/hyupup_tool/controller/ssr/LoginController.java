package com.example.hyupup_tool.controller.ssr;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @GetMapping("/login-page")
    private String loginPage(Model model){
        return "/LoginPage";
    }

    @GetMapping("/signup-page")
    private String signUpPage(Model model){
        return "/SignUpPage";
    }
}
