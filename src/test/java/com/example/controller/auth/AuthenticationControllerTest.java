package com.example.controller.auth;

import com.example.constant.Message;
import com.example.dtos.BaseResponse;
import com.example.dtos.request.SignInRequestDTO;
import com.example.dtos.request.SignUpDTO;
import com.example.dtos.response.SignInResponseDTO;
import com.example.dtos.response.UserDTO;
import com.example.enums.Gender;
import com.example.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {AuthenticationController.class})
@ExtendWith(SpringExtension.class)
class AuthenticationControllerTest {

    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private UserService authenticationService;

    @Test
    void register() throws Exception {
        SignUpDTO signUpDTO = new SignUpDTO(
                "John",
                "Doe",
                "john.doe@example.com",
                "password123",
                30,
                "M",
                "12345678901"
        );

        doNothing().when(authenticationService).signUp(signUpDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signUpDTO));

        MockMvcBuilders.standaloneSetup(authenticationController).build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print(System.out))
                .andReturn();
    }

    @Test
    void login() throws Exception {
        SignInRequestDTO signInRequestDTO = new SignInRequestDTO(
                "john.doe@example.com",
                "password123"
        );

        SignInResponseDTO signInResponseDTO = new SignInResponseDTO(
                "jwt-token",
                new UserDTO(
                        123L,
                        "John Doe",
                        "john.doe@example.com",
                        30,
                        Gender.M,
                        List.of("ROLE_USER"),
                        "johndoe"
                )
        );

        given(authenticationService.signIn(signInRequestDTO)).willReturn(signInResponseDTO);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(signInRequestDTO));

        MvcResult result = MockMvcBuilders.standaloneSetup(authenticationController).build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print(System.out))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        BaseResponse<SignInResponseDTO> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.isSuccess());
        assertEquals(signInResponseDTO.user(), response.getData().user());
    }
}