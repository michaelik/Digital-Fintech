package com.example.controller.transaction;

import com.example.dtos.request.TransferFundDTO;
import com.example.dtos.response.TransactionDTO;
import com.example.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ContextConfiguration(classes = {TransactionController.class})
@ExtendWith(SpringExtension.class)
class TransactionControllerTest {

    @Autowired
    private TransactionController transactionController;

    @MockBean
    private TransactionService transactionService;

    @Test
    void transferFund() throws Exception {
        TransferFundDTO transferRequest = new TransferFundDTO(
                "1234567890",
                new BigDecimal("100.00")
        );

        doNothing().when(transactionService).transferFund(transferRequest);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/v1/transactions/transferFund")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(transferRequest));

        MockMvcBuilders.standaloneSetup(transactionController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print(System.out))
                .andReturn();
    }

    @Test
    void getTransactions() throws Exception {
        List<TransactionDTO> transactions = List.of(
                new TransactionDTO(1L, "Transfer", "John Doe", "1234567890", "BankA", new BigDecimal("100.00")),
                new TransactionDTO(2L, "Deposit", "Jane Doe", "0987654321", "BankB", new BigDecimal("200.00"))
        );
        Page<TransactionDTO> transactionPage = new PageImpl<>(transactions);
        given(transactionService.getTransactions(0, 10)).willReturn(transactionPage);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/v1/transactions")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON);

        MockMvcBuilders.standaloneSetup(transactionController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print(System.out))
                .andReturn();
    }
}