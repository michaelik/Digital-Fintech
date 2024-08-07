package com.example.controller.account;

import com.example.dtos.BaseResponse;
import com.example.dtos.request.FundAccountDTO;
import com.example.dtos.response.AccountDTO;
import com.example.service.AccountService;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {accountController.class})
@ExtendWith(SpringExtension.class)
class AccountControllerTest {

    @Autowired
    private accountController accountController;

    @MockBean
    private AccountService accountService;

    @Test
    void getAccountByUserId() throws Exception {
        long userId = 123L;
        AccountDTO account = new AccountDTO("John Doe", "1234567890", new BigDecimal("5000.00"), "BankA");
        given(accountService.getAccountByUserId(userId)).willReturn(account);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/account/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = MockMvcBuilders.standaloneSetup(accountController).build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print(System.out))
                .andReturn();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        BaseResponse<AccountDTO> response = objectMapper.readValue(
                result.getResponse().getContentAsString(),
                new TypeReference<>() {}
        );

        assertEquals(HttpStatus.OK, response.getStatus());
        assertTrue(response.isSuccess());
        assertEquals(account, response.getData());
    }

    @Test
    void fundAccount() throws Exception {
        long userId = 123L;
        FundAccountDTO fundRequest = new FundAccountDTO(new BigDecimal("100.00"));

        doNothing().when(accountService).fundAccount(userId, fundRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/account/{userId}", userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(fundRequest));

        MockMvcBuilders.standaloneSetup(accountController).build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print(System.out))
                .andReturn();
    }
}