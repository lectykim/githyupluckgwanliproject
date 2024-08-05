package com.example.hyupup_tool;

import com.example.hyupup_tool.controller.UserController;
import com.example.hyupup_tool.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static reactor.core.publisher.Mono.when;

@WebMvcTest(UserController.class)

public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    /*@Test
    @DisplayName("회원가입 성공")
    void 회원가입_확인() throws Exception {
        SignUpRequestDTO signUpRequestDTO = new SignUpRequestDTO("email","pw","access_token");
        ResponseEntity<SuccessResponseDTO> responseEntity =
                ResponseEntity.status(HttpStatus.OK).body(new SuccessResponseDTO("Sign up Success"));

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string(new ObjectMapper().writeValueAsString(successResponseDTO)))
                .andDo(print());
    }*/



}
