package com.test.bankkata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bankkata.api.Controller;
import com.test.bankkata.api.dto.requests.AccountCreationDto;
import com.test.bankkata.api.exceptionHandlers.GlobalExceptionHandler;
import com.test.bankkata.model.Account;
import com.test.bankkata.model.Customer;
import com.test.bankkata.model.enums.AccountType;
import com.test.bankkata.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {
    @InjectMocks
    private Controller controller;

    @Mock
    private AccountService service;
    private MockMvc mvc;
    @BeforeEach
    public  void prepare(){
        mvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    public void createControllerShouldReturnCreatedAccount() throws Exception {
        var objectMapper = new ObjectMapper();
        var account = new Account(0,new Customer("test","test"), BigDecimal.ZERO, Set.of(), LocalDateTime.now(),null, AccountType.RUNNING);
        var accountCreation= objectMapper.writeValueAsBytes(new AccountCreationDto("John","Doe","RUNNING"));
        Mockito.when(service.createAccount(any(Account.class))).thenReturn(account);
        mvc.perform(post("/accounts")
                         .content(accountCreation)
                         .contentType(MediaType.APPLICATION_JSON_VALUE))
                 .andExpect(status().is(201));
    }
    @Test
    public void createControllerShouldReturnBadRequest() throws Exception {

        var objectMapper = new ObjectMapper();
        var accountCreation= objectMapper.writeValueAsBytes(new AccountCreationDto(null,"Doe","RUNNING"));
        var result=mvc.perform(post("/accounts")
                        .content(accountCreation)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400)).andReturn().getResponse();
}
}
