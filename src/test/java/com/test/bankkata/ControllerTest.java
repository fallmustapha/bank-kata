package com.test.bankkata;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.bankkata.api.Controller;
import com.test.bankkata.api.dto.requests.AccountCreationDto;
import com.test.bankkata.api.exceptionHandlers.GlobalExceptionHandler;
import com.test.bankkata.model.Account;
import com.test.bankkata.model.Customer;
import com.test.bankkata.model.Statement;
import com.test.bankkata.model.enums.AccountType;
import com.test.bankkata.services.AccountService;
import org.hamcrest.core.AnyOf;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public void createControllerShouldReturnBadRequestWhenFirstNameIsNull() throws Exception {

        var objectMapper = new ObjectMapper();
        var accountCreation= objectMapper.writeValueAsBytes(new AccountCreationDto(null,"Doe","RUNNING"));
        var result=mvc.perform(post("/accounts")
                        .content(accountCreation)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400)).andReturn().getResponse();
}
    @Test
    public void createControllerShouldReturnBadRequestWhenLastNameIsNull() throws Exception {

        var objectMapper = new ObjectMapper();
        var accountCreation= objectMapper.writeValueAsBytes(new AccountCreationDto("John",null,"RUNNING"));
        var result=mvc.perform(post("/accounts")
                        .content(accountCreation)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400)).andReturn().getResponse();
    }
    @Test
    public void createControllerShouldReturnBadRequestWhenTypeIsNull() throws Exception {

        var objectMapper = new ObjectMapper();
        var accountCreation= objectMapper.writeValueAsBytes(new AccountCreationDto("John","doe",null));
        var result=mvc.perform(post("/accounts")
                        .content(accountCreation)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400)).andReturn().getResponse();
    }
    @Test
    public void createControllerShouldReturnBadRequestWhenTheTypeIsInvalid() throws Exception {

        var objectMapper = new ObjectMapper();
        var accountCreation= objectMapper.writeValueAsBytes(new AccountCreationDto("John","doe","MyType"));
        var result=mvc.perform(post("/accounts")
                        .content(accountCreation)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400)).andReturn().getResponse();
    }

    @Test
    public void makeDepositShouldBeOk() throws Exception{
        var accountNumber=645465l;
        var amount="500";
        Mockito.doNothing().when(service).makeDeposit(eq(accountNumber),any(BigDecimal.class));
        mvc.perform(put("/accounts/"+accountNumber+"/deposit")
                        .content("The deposit of has successfully been processed")
                        .param("amount",amount)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    public void makeDepositShouldReturnBadRequestWhenAccountIsNegative() throws Exception{
        var accountNumber=-1l;
        var amount="500";
        mvc.perform(put("/accounts/"+accountNumber+"/deposit")
                        .param("amount",amount)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }
    @Test
    public void makeDepositShouldReturnBadRequestWhenAmountIsNegative() throws Exception{
        var accountNumber=0547l;
        var amount="-1";
        mvc.perform(put("/accounts/"+accountNumber+"/deposit")
                        .param("amount",amount)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }
    @Test
    public void makeDepositShouldReturnBadRequestWhenAmountIsNull() throws Exception{
        var accountNumber=0547l;
        var amount="0";
        mvc.perform(put("/accounts/"+accountNumber+"/deposit")
                        .param("amount",amount)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }
    @Test
    public void makeDepositShouldReturnServerErrorWhenServiceThrowsException() throws Exception{
        var accountNumber=0547l;
        var amount="200";
        Mockito.doThrow(RuntimeException.class).when(service).makeDeposit(eq(accountNumber),any(BigDecimal.class));
        mvc.perform(put("/accounts/"+accountNumber+"/deposit")
                        .param("amount",amount)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(500));
    }


    @Test
    public void makeWithdrawShouldBeOk() throws Exception{
        var accountNumber=645465l;
        var amount="500";
        Mockito.doNothing().when(service).makeWithdraw(eq(accountNumber),any(BigDecimal.class));
        mvc.perform(put("/accounts/"+accountNumber+"/withdraw")
                        .content("The debit of your account has successfully been processed")
                        .param("amount",amount)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200));
    }

    @Test
    public void makeWithdrawShouldReturnBadRequestWhenAccountIsNegative() throws Exception{
        var accountNumber=-1l;
        var amount="500";
        mvc.perform(put("/accounts/"+accountNumber+"/withdraw")
                        .param("amount",amount)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }
    @Test
    public void makeWithdrawShouldReturnBadRequestWhenAmountIsNegative() throws Exception{
        var accountNumber=0547l;
        var amount="-1";
        mvc.perform(put("/accounts/"+accountNumber+"/withdraw")
                        .param("amount",amount)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }

    @Test
    public void makeWithdrawShouldReturnBadRequestWhenAmountIsNull() throws Exception{
        var accountNumber=0547l;
        var amount="0";
        mvc.perform(put("/accounts/"+accountNumber+"/withdraw")
                        .param("amount",amount)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(400));
    }

    @Test
    public void makeWithdrawShouldReturnServerErrorWhenServiceThrowsException() throws Exception{
        var accountNumber=0547l;
        var amount="200";
        Mockito.doThrow(RuntimeException.class).when(service).makeWithdraw(eq(accountNumber),any(BigDecimal.class));
        mvc.perform(put("/accounts/"+accountNumber+"/withdraw")
                        .param("amount",amount)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(500));
    }

    @Test
    public void getHistoryShouldReturnOk() throws Exception{
        var accountNumber=645465l;
        Mockito.when(service.getHistory(accountNumber)).thenReturn(Set.of(new Statement(LocalDateTime.now(),BigDecimal.valueOf(10),BigDecimal.TEN)));
        var result=mvc.perform(get("/accounts/"+accountNumber+"/history")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(200)).andReturn().getResponse();
    }
    @Test
    public void getHistoryShouldReturnServerErrorWhenServiceThrowsError() throws Exception{
        var accountNumber=645465l;
        Mockito.when(service.getHistory(accountNumber)).thenThrow(RuntimeException.class);
        var result=mvc.perform(get("/accounts/"+accountNumber+"/history")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is(500)).andReturn().getResponse();
    }
}
