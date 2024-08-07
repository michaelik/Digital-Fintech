package com.example.controller.user;

import com.example.dtos.BaseResponse;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {userController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private userController userController;

    @MockBean
    private UserService userService;

    @Test
    void getUserById() throws Exception {
        long userId = 123L;
        UserDTO user = new UserDTO(
                userId,
                "John Doe",
                "john.doe@example.com",
                30,
                Gender.M,
                List.of("ROLE_USER"),
                "johndoe"
        );

        given(userService.getUserById(userId)).willReturn(user);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/user/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = MockMvcBuilders.standaloneSetup(userController).build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print(System.out))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        BaseResponse<UserDTO> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.isSuccess());
        assertEquals(user, response.getData());
    }
}